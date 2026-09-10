package app.lawnchair.data.usage

import android.content.Context
import android.content.Intent
import android.os.Build
import app.lawnchair.data.AppDatabase
import com.android.launcher3.util.MainThreadInitializedObject
import com.android.launcher3.util.SafeCloseable
import java.util.concurrent.atomic.AtomicInteger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

class UsageService(private val context: Context) : SafeCloseable {
    private val dao = AppDatabase.INSTANCE.get(context).usageDao()
    private val serialTick = MutableStateFlow(0)
    private val lastSyncedInterval = AtomicInteger(Int.MIN_VALUE)

    fun observeUiState(): Flow<UsageAuditUiState> {
        val date = UsageDates.today()
        val pulse = flow {
            while (true) {
                emit(Unit)
                delay(2_000)
            }
        }
        return combine(
            combine(
                dao.observeIdentity(),
                dao.observeWatched(),
                dao.observeDeviceUsage(date),
                dao.observeAppUsage(date),
                dao.observeRecentPings(20),
            ) { identity, watched, device, apps, pings ->
                UsageQuery(identity, watched, device, apps, pings)
            },
            serialTick,
            pulse,
        ) { query, _, _ ->
            snapshot(query)
        }
    }

    private fun snapshot(query: UsageQuery): UsageAuditUiState {
        val serial = DeviceSerial.resolve(context)
        val intervalMin = PingInterval.resolve(context)
        if (lastSyncedInterval.getAndSet(intervalMin) != intervalMin) {
            PingScheduler.sync(context)
        }
        return UsageAuditUiState(
            tabletId = serial ?: query.identity?.tabletId,
            serialValid = serial != null,
            serialSource = DeviceSerial.source(context),
            debugOverride = DeviceSerial.debugOverride(context),
            model = query.identity?.model ?: Build.MODEL.orEmpty(),
            hasUsagePermission = UsagePermission.hasAccess(context),
            hasLocationPermission = LocationPermission.hasFine(context),
            screenOnMs = query.device?.screenOnMs ?: 0L,
            pingIntervalMin = intervalMin,
            pingIntervalSource = PingInterval.source(context),
            debugPingInterval = PingInterval.debugOverride(context),
            routeActive = PingInterval.isRoute(intervalMin) &&
                serial != null &&
                LocationPermission.hasFine(context),
            recentPings = query.pings,
            watched = query.watched.associateBy { it.packageName },
            knoxWatched = WatchedPackages.isControlled(context),
            appUsageByPackage = query.apps.associateBy { it.packageName },
            syncConfigured = LauncherApiConfig.isConfigured(context),
            lastSyncAt = LauncherAuthStore(context).lastSyncAt(),
            username = LauncherApiConfig.username(context),
            debugApiUrl = LauncherApiConfig.debugUrl(context),
            debugBootstrap = LauncherApiConfig.debugSecret(context),
            debugUsername = LauncherApiConfig.debugUsername(context),
        )
    }

    fun refresh() {
        serialTick.update { it + 1 }
        PingScheduler.sync(context)
    }

    suspend fun collectToday(): Boolean = withContext(Dispatchers.IO) {
        val collected = UsageCollector.collectToday(context, dao)
        LauncherSyncWorker.enqueueOnce(context)
        collected
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

    suspend fun setDebugPingInterval(raw: String) {
        PingInterval.setDebugOverride(context, raw)
        refresh()
    }

    suspend fun setWatched(packageName: String, label: String, enabled: Boolean) = withContext(Dispatchers.IO) {
        if (WatchedPackages.isControlled(context)) return@withContext
        if (enabled) {
            dao.upsertWatched(WatchedApp(packageName = packageName, label = label, enabled = true))
        } else {
            dao.deleteWatched(packageName)
        }
    }

    fun hasUsagePermission(): Boolean = UsagePermission.hasAccess(context)

    fun hasLocationPermission(): Boolean = LocationPermission.hasFine(context)

    fun usageAccessIntent(): Intent = UsagePermission.settingsIntent()

    fun locationSettingsIntent(): Intent = LocationPermission.settingsIntent(context)

    override fun close() = Unit

    private data class UsageQuery(
        val identity: DeviceIdentity?,
        val watched: List<WatchedApp>,
        val device: DailyDeviceUsage?,
        val apps: List<DailyAppUsage>,
        val pings: List<LocationPing>,
    )

    companion object {
        @JvmField
        val INSTANCE = MainThreadInitializedObject(::UsageService)
    }
}
