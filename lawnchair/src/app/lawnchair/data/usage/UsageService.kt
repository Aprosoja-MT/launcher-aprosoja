package app.lawnchair.data.usage

import android.content.Context
import android.content.Intent
import android.os.Build
import app.lawnchair.data.AppDatabase
import com.android.launcher3.util.MainThreadInitializedObject
import com.android.launcher3.util.SafeCloseable
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class UsageService(private val context: Context) : SafeCloseable {
    private val dao = AppDatabase.INSTANCE.get(context).usageDao()

    @Volatile
    private var lastCollectAt = 0L

    @OptIn(ExperimentalCoroutinesApi::class)
    fun observeUiState(): Flow<UsageAuditUiState> {
        return ticker()
            .map { UsageDates.today() }
            .distinctUntilChanged()
            .flatMapLatest { date -> observeQuery(date) }
            .combine(ticker()) { query, _ -> snapshot(query) }
    }

    private fun ticker(): Flow<Unit> = flow {
        while (true) {
            emit(Unit)
            delay(PULSE_MS)
        }
    }

    private fun observeQuery(date: String): Flow<UsageQuery> = combine(
        dao.observeIdentity(),
        dao.observeWatched(),
        dao.observeDeviceUsage(date),
        dao.observeAppUsage(date),
        dao.observeRecentPings(RECENT_PINGS),
    ) { identity, watched, device, apps, pings ->
        UsageQuery(identity, watched, device, apps, pings)
    }

    private fun snapshot(query: UsageQuery): UsageAuditUiState {
        val serial = DeviceSerial.resolved(context)
        val authStore = LauncherAuthStore(context)
        return UsageAuditUiState(
            tabletId = serial.value ?: query.identity?.tabletId,
            serialValid = serial.value != null,
            serialSource = serial.source,
            debugOverride = DeviceSerial.debugOverride(context),
            model = query.identity?.model ?: Build.MODEL.orEmpty(),
            grantedPermissions = AuditPermission.granted(context),
            screenOnMs = query.device?.screenOnMs ?: 0L,
            pingMode = MovementState.mode(context),
            recentPings = query.pings,
            watched = query.watched.associateBy { it.packageName },
            knoxWatched = WatchedPackages.isControlled(context),
            appUsageByPackage = query.apps.associateBy { it.packageName },
            syncConfigured = LauncherApiConfig.isConfigured(context),
            deviceRegistered = authStore.token() != null && authStore.tabletId() == serial.value,
            lastSyncAt = authStore.lastSyncAt(),
            lastSyncError = authStore.lastError(),
            username = LauncherApiConfig.username(context),
            debugApiUrl = LauncherApiConfig.debugUrl(context),
            debugBootstrap = LauncherApiConfig.debugSecret(context),
            debugUsername = LauncherApiConfig.debugUsername(context),
        )
    }

    fun refresh() {
        PingScheduler.sync(context)
    }

    suspend fun collectToday(): Boolean = withContext(Dispatchers.IO) {
        lastCollectAt = System.currentTimeMillis()
        val collected = UsageCollector.collectToday(context, dao)
        LauncherSyncWorker.enqueueOnce(context)
        collected
    }

    suspend fun collectTodayIfStale(): Boolean {
        if (System.currentTimeMillis() - lastCollectAt < COLLECT_THROTTLE_MS) return false
        return collectToday()
    }

    suspend fun syncNow(): Boolean = withContext(Dispatchers.IO) {
        LauncherSyncClient.sync(context, dao)
    }

    fun setDebugApiUrl(value: String) {
        LauncherApiConfig.setDebugUrl(context, value)
        refresh()
    }

    fun setDebugBootstrap(value: String) {
        LauncherApiConfig.setDebugSecret(context, value)
        refresh()
    }

    fun setDebugUsername(value: String) {
        LauncherApiConfig.setDebugUsername(context, value)
        refresh()
    }

    suspend fun collectPing(): Boolean = withContext(Dispatchers.IO) {
        PingCollector.collectOnce(context, dao)
    }

    suspend fun setDebugSerial(serial: String) {
        DeviceSerial.setDebugOverride(context, serial)
        refresh()
        collectToday()
    }

    suspend fun setWatched(packageName: String, label: String, enabled: Boolean) = withContext(Dispatchers.IO) {
        if (WatchedPackages.isControlled(context)) return@withContext
        if (enabled) {
            dao.upsertWatched(WatchedApp(packageName = packageName, label = label))
        } else {
            dao.deleteWatched(packageName)
        }
    }

    fun hasLocationPermission(): Boolean = LocationPermission.hasFine(context)

    fun usageAccessIntent(): Intent = UsagePermission.settingsIntent()

    fun locationSettingsIntent(): Intent = LocationPermission.settingsIntent(context)

    fun batteryOptimizationIntent(): Intent = BatteryOptimization.requestIntent(context)

    override fun close() = Unit

    private data class UsageQuery(
        val identity: DeviceIdentity?,
        val watched: List<WatchedApp>,
        val device: DailyDeviceUsage?,
        val apps: List<DailyAppUsage>,
        val pings: List<LocationPing>,
    )

    companion object {
        private const val PULSE_MS = 2_000L
        private const val RECENT_PINGS = 20
        private val COLLECT_THROTTLE_MS = TimeUnit.MINUTES.toMillis(PingRules.COLLECT_THROTTLE_MINUTES)

        @JvmField
        val INSTANCE = MainThreadInitializedObject(::UsageService)
    }
}
