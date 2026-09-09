package app.lawnchair.data.usage

data class UsageAuditUiState(
    val tabletId: String?,
    val serialValid: Boolean,
    val serialSource: SerialSource,
    val debugOverride: String,
    val model: String,
    val hasUsagePermission: Boolean,
    val hasLocationPermission: Boolean,
    val screenOnMs: Long,
    val pingIntervalMin: Int,
    val pingIntervalSource: PingIntervalSource,
    val debugPingInterval: String,
    val routeActive: Boolean,
    val recentPings: List<LocationPing>,
    val watched: Map<String, WatchedApp>,
    val knoxWatched: Boolean,
    val appUsageByPackage: Map<String, DailyAppUsage>,
) {
    companion object {
        val Empty = UsageAuditUiState(
            tabletId = null,
            serialValid = false,
            serialSource = SerialSource.NONE,
            debugOverride = "",
            model = "",
            hasUsagePermission = false,
            hasLocationPermission = false,
            screenOnMs = 0L,
            pingIntervalMin = 15,
            pingIntervalSource = PingIntervalSource.DEFAULT,
            debugPingInterval = "",
            routeActive = false,
            recentPings = emptyList(),
            watched = emptyMap(),
            knoxWatched = false,
            appUsageByPackage = emptyMap(),
        )
    }
}
