package app.lawnchair.data.usage

import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context
import androidx.core.content.getSystemService

object UsageEventReader {
    private const val EVENT_DEVICE_SHUTDOWN = 26
    private const val EVENT_DEVICE_STARTUP = 27

    fun read(context: Context, startMs: Long, endMs: Long): List<UsageEvent>? {
        val manager = context.getSystemService<UsageStatsManager>() ?: return null
        val cursor = manager.queryEvents(startMs, endMs)
        val event = UsageEvents.Event()
        val events = mutableListOf<UsageEvent>()
        while (cursor.hasNextEvent()) {
            cursor.getNextEvent(event)
            val type = mapType(event.eventType) ?: continue
            val packageName = event.packageName
            if (type.requiresPackage && packageName == null) continue
            events += UsageEvent(
                timestamp = event.timeStamp,
                type = type,
                packageName = packageName,
                className = event.className,
            )
        }
        return events
    }

    private fun mapType(eventType: Int): UsageEventType? = when (eventType) {
        UsageEvents.Event.ACTIVITY_RESUMED -> UsageEventType.ACTIVITY_RESUMED
        UsageEvents.Event.ACTIVITY_PAUSED -> UsageEventType.ACTIVITY_PAUSED
        UsageEvents.Event.ACTIVITY_STOPPED -> UsageEventType.ACTIVITY_STOPPED
        UsageEvents.Event.SCREEN_INTERACTIVE -> UsageEventType.SCREEN_INTERACTIVE
        UsageEvents.Event.SCREEN_NON_INTERACTIVE -> UsageEventType.SCREEN_NON_INTERACTIVE
        UsageEvents.Event.KEYGUARD_SHOWN -> UsageEventType.KEYGUARD_SHOWN
        UsageEvents.Event.KEYGUARD_HIDDEN -> UsageEventType.KEYGUARD_HIDDEN
        EVENT_DEVICE_SHUTDOWN -> UsageEventType.DEVICE_SHUTDOWN
        EVENT_DEVICE_STARTUP -> UsageEventType.DEVICE_STARTUP
        else -> null
    }
}
