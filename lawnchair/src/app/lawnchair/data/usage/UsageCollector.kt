package app.lawnchair.data.usage

import android.content.Context
import android.os.Build
import android.os.PowerManager
import androidx.core.content.getSystemService

object UsageCollector {
    private const val RECOMPUTE_DAYS = 2
    private const val LOOKBACK_MS = 24 * 60 * 60 * 1000L

    suspend fun collectToday(context: Context, dao: UsageDao): Boolean {
        WatchedPackages.sync(context, dao)
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

        val now = System.currentTimeMillis()
        val days = UsageDates.recentDays(RECOMPUTE_DAYS, now)
        val windowStart = days.first().startMs - LOOKBACK_MS
        val events = UsageEventReader.read(context, windowStart, now) ?: return false
        val screenInteractive = context.getSystemService<PowerManager>()?.isInteractive
        val sweeps = UsageSweep.run(events, days, windowStart, now, screenInteractive)

        val watched = dao.getWatched()
        for (sweep in sweeps) {
            if (!sweep.shouldPersist(hasStaleAppUsage(dao, sweep.date, watched))) continue
            persist(dao, sweep, watched, now)
        }

        val cutoff = UsageDates.pruneCutoff()
        dao.pruneDeviceUsage(cutoff)
        dao.pruneAppUsage(cutoff)
        return true
    }

    private suspend fun hasStaleAppUsage(
        dao: UsageDao,
        date: String,
        watched: List<WatchedApp>,
    ): Boolean {
        return watched.any { app ->
            (dao.getAppUsage(date, app.packageName)?.foregroundMs ?: 0L) > 0L
        }
    }

    private suspend fun persist(
        dao: UsageDao,
        sweep: UsageDaySweep,
        watched: List<WatchedApp>,
        now: Long,
    ) {
        val existingDevice = dao.getDeviceUsage(sweep.date)
        if (existingDevice == null || existingDevice.screenOnMs != sweep.screenOnMs) {
            dao.upsertDeviceUsage(
                DailyDeviceUsage(
                    date = sweep.date,
                    screenOnMs = sweep.screenOnMs,
                    updatedAt = now,
                    syncedAt = existingDevice?.syncedAt ?: 0L,
                ),
            )
        }
        for (app in watched) {
            val usage = sweep.packages[app.packageName]
            val openCount = usage?.openCount ?: 0
            val foregroundMs = usage?.foregroundMs ?: 0L
            val existingApp = dao.getAppUsage(sweep.date, app.packageName)
            if (existingApp != null &&
                existingApp.openCount == openCount &&
                existingApp.foregroundMs == foregroundMs
            ) {
                continue
            }
            dao.upsertAppUsage(
                DailyAppUsage(
                    date = sweep.date,
                    packageName = app.packageName,
                    openCount = openCount,
                    foregroundMs = foregroundMs,
                    updatedAt = now,
                    syncedAt = existingApp?.syncedAt ?: 0L,
                ),
            )
        }
    }
}
