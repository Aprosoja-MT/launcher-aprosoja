package app.lawnchair.data.usage

import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context
import android.os.Build
import androidx.core.content.getSystemService

object UsageCollector {
    suspend fun collectToday(context: Context, dao: UsageDao): Boolean {
        val serial = DeviceSerial.resolve(context) ?: return false
        val existing = dao.getIdentity()
        if (existing == null || existing.tabletId != serial) {
            dao.upsertIdentity(
                DeviceIdentity(
                    tabletId = serial,
                    model = Build.MODEL.orEmpty(),
                    registeredAt = existing?.registeredAt ?: System.currentTimeMillis(),
                ),
            )
        }
        if (!UsagePermission.hasAccess(context)) return false

        val usageStatsManager = context.getSystemService<UsageStatsManager>() ?: return false
        val start = UsageDates.startOfDayMillis()
        val now = System.currentTimeMillis()
        val date = UsageDates.today()
        val events = usageStatsManager.queryEvents(start, now)
        val screenOnMs = screenOnDurationMs(events, start, now)
        dao.upsertDeviceUsage(DailyDeviceUsage(date = date, screenOnMs = screenOnMs))

        val watched = dao.getEnabledWatched()
        if (watched.isNotEmpty()) {
            val openCounts = openCounts(usageStatsManager, start, now, watched.map { it.packageName }.toSet())
            val foreground = usageStatsManager.queryAndAggregateUsageStats(start, now)
            for (app in watched) {
                val stats = foreground[app.packageName]
                dao.upsertAppUsage(
                    DailyAppUsage(
                        date = date,
                        packageName = app.packageName,
                        openCount = openCounts[app.packageName] ?: 0,
                        foregroundMs = stats?.totalTimeInForeground ?: 0L,
                    ),
                )
            }
        }

        val cutoff = UsageDates.pruneCutoff()
        dao.pruneDeviceUsage(cutoff)
        dao.pruneAppUsage(cutoff)
        return true
    }

    private fun screenOnDurationMs(
        events: UsageEvents,
        start: Long,
        now: Long,
    ): Long {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) return 0L
        val event = UsageEvents.Event()
        var onSince: Long? = null
        var total = 0L
        while (events.hasNextEvent()) {
            events.getNextEvent(event)
            when (event.eventType) {
                UsageEvents.Event.SCREEN_INTERACTIVE -> {
                    onSince = event.timeStamp
                }
                UsageEvents.Event.SCREEN_NON_INTERACTIVE -> {
                    val from = onSince ?: start
                    if (event.timeStamp > from) {
                        total += event.timeStamp - from
                    }
                    onSince = null
                }
            }
        }
        if (onSince != null && now > onSince) {
            total += now - onSince
        }
        return total
    }

    private fun openCounts(
        usageStatsManager: UsageStatsManager,
        start: Long,
        now: Long,
        packages: Set<String>,
    ): Map<String, Int> {
        val events = usageStatsManager.queryEvents(start, now)
        val event = UsageEvents.Event()
        val counts = mutableMapOf<String, Int>()
        val resumeType = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            UsageEvents.Event.ACTIVITY_RESUMED
        } else {
            UsageEvents.Event.MOVE_TO_FOREGROUND
        }
        while (events.hasNextEvent()) {
            events.getNextEvent(event)
            if (event.eventType != resumeType) continue
            val packageName = event.packageName ?: continue
            if (packageName !in packages) continue
            counts[packageName] = (counts[packageName] ?: 0) + 1
        }
        return counts
    }
}
