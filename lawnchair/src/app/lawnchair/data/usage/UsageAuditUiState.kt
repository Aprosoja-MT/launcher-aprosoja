package app.lawnchair.data.usage

data class UsageAuditUiState(
    val tabletId: String?,
    val serialValid: Boolean,
    val serialSource: SerialSource,
    val debugOverride: String,
    val model: String,
    val grantedPermissions: Set<AuditPermission>,
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
    val knox: KnoxProfile = KnoxProfile.Empty,
    val specs: DeviceSpecsSnapshot = DeviceSpecsSnapshot.Empty,
) {
    fun isGranted(permission: AuditPermission): Boolean = permission in grantedPermissions

    companion object {
        val Empty = UsageAuditUiState(
            tabletId = null,
            serialValid = false,
            serialSource = SerialSource.NONE,
            debugOverride = "",
            model = "",
            grantedPermissions = emptySet(),
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
            knox = KnoxProfile.Empty,
            specs = DeviceSpecsSnapshot.Empty,
        )
    }
}
