package app.lawnchair.data.usage

import android.content.Context
import android.content.Intent
import android.os.Build
import app.lawnchair.data.AppDatabase
import com.android.launcher3.util.MainThreadInitializedObject
import com.android.launcher3.util.SafeCloseable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

class UsageService(private val context: Context) : SafeCloseable {
    private val dao = AppDatabase.INSTANCE.get(context).usageDao()
    private val serialTick = MutableStateFlow(0)

    fun observeUiState(): Flow<UsageAuditUiState> {
        val date = UsageDates.today()
        return combine(
            dao.observeIdentity(),
            dao.observeWatched(),
            dao.observeDeviceUsage(date),
            dao.observeAppUsage(date),
            serialTick,
        ) { identity, watched, device, apps, _ ->
            val serial = DeviceSerial.resolve(context)
            UsageAuditUiState(
                tabletId = serial ?: identity?.tabletId,
                serialValid = serial != null,
                serialSource = DeviceSerial.source(context),
                debugOverride = DeviceSerial.debugOverride(context),
                model = identity?.model ?: Build.MODEL.orEmpty(),
                hasUsagePermission = UsagePermission.hasAccess(context),
                screenOnMs = device?.screenOnMs ?: 0L,
                watched = watched.associateBy { it.packageName },
                appUsageByPackage = apps.associateBy { it.packageName },
            )
        }
    }

    fun refresh() {
        serialTick.update { it + 1 }
    }

    suspend fun collectToday(): Boolean = withContext(Dispatchers.IO) {
        UsageCollector.collectToday(context, dao)
    }

    suspend fun setDebugSerial(serial: String) {
        DeviceSerial.setDebugOverride(context, serial)
        refresh()
        collectToday()
    }

    suspend fun setWatched(packageName: String, label: String, enabled: Boolean) = withContext(Dispatchers.IO) {
        if (enabled) {
            dao.upsertWatched(WatchedApp(packageName = packageName, label = label, enabled = true))
        } else {
            dao.deleteWatched(packageName)
        }
    }

    fun hasUsagePermission(): Boolean = UsagePermission.hasAccess(context)

    fun usageAccessIntent(): Intent = UsagePermission.settingsIntent()

    override fun close() = Unit

    companion object {
        @JvmField
        val INSTANCE = MainThreadInitializedObject(::UsageService)
    }
}
