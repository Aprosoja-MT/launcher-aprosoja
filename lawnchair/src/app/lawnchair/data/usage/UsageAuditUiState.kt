package app.lawnchair.data.usage

data class UsageAuditUiState(
    val tabletId: String?,
    val serialValid: Boolean,
    val serialSource: SerialSource,
    val debugOverride: String,
    val model: String,
    val hasUsagePermission: Boolean,
    val hasLocationPermission: Boolean,
    val hasBackgroundLocationPermission: Boolean,
    val hasNotificationPermission: Boolean,
    val batteryOptimizationIgnored: Boolean,
    val screenOnMs: Long,
    val pingMode: PingMode,
    val recentPings: List<LocationPing>,
    val watched: Map<String, WatchedApp>,
    val knoxWatched: Boolean,
    val appUsageByPackage: Map<String, DailyAppUsage>,
    val syncConfigured: Boolean,
    val deviceRegistered: Boolean,
    val lastSyncAt: Long,
    val lastSyncError: String?,
    val username: String?,
    val debugApiUrl: String,
    val debugBootstrap: String,
    val debugUsername: String,
) {
    fun isGranted(permission: AuditPermission): Boolean = when (permission) {
        AuditPermission.USAGE_ACCESS -> hasUsagePermission
        AuditPermission.LOCATION -> hasLocationPermission
        AuditPermission.BACKGROUND_LOCATION -> hasBackgroundLocationPermission
        AuditPermission.NOTIFICATIONS -> hasNotificationPermission
        AuditPermission.BATTERY -> batteryOptimizationIgnored
    }

    companion object {
        val Empty = UsageAuditUiState(
            tabletId = null,
            serialValid = false,
            serialSource = SerialSource.NONE,
            debugOverride = "",
            model = "",
            hasUsagePermission = false,
            hasLocationPermission = false,
            hasBackgroundLocationPermission = false,
            hasNotificationPermission = false,
            batteryOptimizationIgnored = false,
            screenOnMs = 0L,
            pingMode = PingMode.IDLE,
            recentPings = emptyList(),
            watched = emptyMap(),
            knoxWatched = false,
            appUsageByPackage = emptyMap(),
            syncConfigured = false,
            deviceRegistered = false,
            lastSyncAt = 0L,
            lastSyncError = null,
            username = null,
            debugApiUrl = "",
            debugBootstrap = "",
            debugUsername = "",
        )
    }
}
