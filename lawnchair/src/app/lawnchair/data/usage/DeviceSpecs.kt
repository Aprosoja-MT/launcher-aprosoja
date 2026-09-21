package app.lawnchair.data.usage

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.BatteryManager
import android.os.Build
import android.os.Environment
import android.os.StatFs
import androidx.core.content.getSystemService
import com.android.launcher3.BuildConfig

data class DeviceSpecsSnapshot(
    val appVersion: String? = null,
    val androidVersion: String? = null,
    val sdkInt: Int? = null,
    val securityPatch: String? = null,
    val manufacturer: String? = null,
    val ramTotalBytes: Long? = null,
    val storageTotalBytes: Long? = null,
    val screenResolution: String? = null,
    val batteryLevel: Int? = null,
    val batteryCharging: Boolean? = null,
    val storageFreeBytes: Long? = null,
    val networkType: String? = null,
) {
    companion object {
        val Empty = DeviceSpecsSnapshot()
    }
}

object DeviceSpecs {
    private const val NETWORK_WIFI = "wifi"
    private const val NETWORK_MOBILE = "mobile"
    private const val NETWORK_OFFLINE = "offline"

    fun read(context: Context): DeviceSpecsSnapshot {
        val storage = dataStats()
        val battery = batteryStatus(context)
        return DeviceSpecsSnapshot(
            appVersion = BuildConfig.VERSION_NAME.takeIf { it.isNotBlank() },
            androidVersion = Build.VERSION.RELEASE?.takeIf { it.isNotBlank() },
            sdkInt = Build.VERSION.SDK_INT,
            securityPatch = Build.VERSION.SECURITY_PATCH?.takeIf { it.isNotBlank() },
            manufacturer = Build.MANUFACTURER?.takeIf { it.isNotBlank() },
            ramTotalBytes = ramTotalBytes(context),
            storageTotalBytes = storage?.let { runCatching { it.totalBytes }.getOrNull() },
            screenResolution = screenResolution(context),
            batteryLevel = battery?.first,
            batteryCharging = battery?.second,
            storageFreeBytes = storage?.let { runCatching { it.availableBytes }.getOrNull() },
            networkType = networkType(context),
        )
    }

    private fun dataStats(): StatFs? = runCatching {
        StatFs(Environment.getDataDirectory().path)
    }.getOrNull()

    private fun ramTotalBytes(context: Context): Long? = runCatching {
        val manager = context.getSystemService<ActivityManager>() ?: return null
        val info = ActivityManager.MemoryInfo()
        manager.getMemoryInfo(info)
        info.totalMem
    }.getOrNull()

    private fun screenResolution(context: Context): String? = runCatching {
        val metrics = context.resources.displayMetrics
        "${metrics.widthPixels}x${metrics.heightPixels}"
    }.getOrNull()

    private fun batteryStatus(context: Context): Pair<Int?, Boolean?>? = runCatching {
        val intent = context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
            ?: return null
        val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
        val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
        val percent = if (level >= 0 && scale > 0) level * 100 / scale else null
        val plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)
        val charging = if (plugged >= 0) plugged > 0 else null
        percent to charging
    }.getOrNull()

    private fun networkType(context: Context): String? = runCatching {
        val manager = context.getSystemService<ConnectivityManager>() ?: return null
        val network = manager.activeNetwork ?: return NETWORK_OFFLINE
        val capabilities = manager.getNetworkCapabilities(network) ?: return NETWORK_OFFLINE
        when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> NETWORK_WIFI
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> NETWORK_MOBILE
            else -> NETWORK_OFFLINE
        }
    }.getOrNull()
}
