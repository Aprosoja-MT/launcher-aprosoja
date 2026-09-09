package app.lawnchair.data.usage

data class UsageAuditUiState(
    val tabletId: String?,
    val serialValid: Boolean,
    val serialSource: SerialSource,
    val debugOverride: String,
    val model: String,
    val hasUsagePermission: Boolean,
    val screenOnMs: Long,
    val watched: Map<String, WatchedApp>,
    val appUsageByPackage: Map<String, DailyAppUsage>,
)
