package app.lawnchair.data.usage

enum class UsageEventType(val requiresPackage: Boolean) {
    ACTIVITY_RESUMED(true),
    ACTIVITY_PAUSED(true),
    ACTIVITY_STOPPED(true),
    SCREEN_INTERACTIVE(false),
    SCREEN_NON_INTERACTIVE(false),
    KEYGUARD_SHOWN(false),
    KEYGUARD_HIDDEN(false),
    DEVICE_SHUTDOWN(false),
    DEVICE_STARTUP(false),
}

data class UsageEvent(
    val timestamp: Long,
    val type: UsageEventType,
    val packageName: String? = null,
    val className: String? = null,
)

data class UsageDayWindow(
    val date: String,
    val startMs: Long,
    val endMs: Long,
)

data class UsagePackageUsage(
    val foregroundMs: Long,
    val openCount: Int,
)

data class UsageDaySweep(
    val date: String,
    val screenOnMs: Long,
    val sawScreenEvent: Boolean,
    val packages: Map<String, UsagePackageUsage>,
) {
    fun shouldPersist(hasStaleAppUsage: Boolean): Boolean {
        return sawScreenEvent || screenOnMs > 0L || hasStaleAppUsage
    }
}
