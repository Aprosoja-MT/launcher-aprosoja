package app.lawnchair.data.usage

import android.content.Context

enum class AuditPermission {
    USAGE_ACCESS,
    LOCATION,
    BACKGROUND_LOCATION,
    NOTIFICATIONS,
    BATTERY,
    ;

    fun isRequired(): Boolean = when (this) {
        BACKGROUND_LOCATION -> LocationPermission.needsBackground()
        NOTIFICATIONS -> NotificationPermission.isRequired()
        else -> true
    }

    fun isGranted(context: Context): Boolean = when (this) {
        USAGE_ACCESS -> UsagePermission.hasAccess(context)
        LOCATION -> LocationPermission.hasFine(context)
        BACKGROUND_LOCATION -> LocationPermission.hasBackground(context)
        NOTIFICATIONS -> NotificationPermission.has(context)
        BATTERY -> BatteryOptimization.isIgnoring(context)
    }

    companion object {
        fun required(): List<AuditPermission> = entries.filter { it.isRequired() }

        fun missing(context: Context): List<AuditPermission> = required().filterNot { it.isGranted(context) }
    }
}
