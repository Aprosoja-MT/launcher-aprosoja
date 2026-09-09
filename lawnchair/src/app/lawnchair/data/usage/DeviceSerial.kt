package app.lawnchair.data.usage

import android.content.Context
import android.content.RestrictionsManager
import android.os.Build
import com.android.launcher3.BuildConfig

enum class SerialSource {
    KNOX,
    BUILD,
    DEBUG,
    NONE,
}

object DeviceSerial {
    const val RESTRICTION_KEY = "tablet_serial"

    private const val DEBUG_PREFS = "usage_audit"
    private const val DEBUG_KEY = "debug_serial"

    fun resolve(context: Context): String? {
        return fromRestrictions(context)
            ?: fromBuild()
            ?: fromDebugOverride(context)
    }

    fun source(context: Context): SerialSource {
        fromRestrictions(context)?.let { return SerialSource.KNOX }
        fromBuild()?.let { return SerialSource.BUILD }
        fromDebugOverride(context)?.let { return SerialSource.DEBUG }
        return SerialSource.NONE
    }

    fun debugOverride(context: Context): String {
        if (!BuildConfig.DEBUG) return ""
        return context.getSharedPreferences(DEBUG_PREFS, Context.MODE_PRIVATE)
            .getString(DEBUG_KEY, "")
            .orEmpty()
    }

    fun setDebugOverride(context: Context, serial: String) {
        if (!BuildConfig.DEBUG) return
        context.getSharedPreferences(DEBUG_PREFS, Context.MODE_PRIVATE)
            .edit()
            .putString(DEBUG_KEY, serial.trim())
            .apply()
    }

    fun isValid(serial: String?): Boolean {
        if (serial.isNullOrBlank()) return false
        if (serial.equals("unknown", ignoreCase = true)) return false
        return serial.any { it != '0' }
    }

    private fun fromRestrictions(context: Context): String? {
        val restrictions = context.getSystemService(RestrictionsManager::class.java)
            ?.applicationRestrictions
            ?: return null
        return restrictions.getString(RESTRICTION_KEY)?.takeIf { isValid(it) }
    }

    private fun fromBuild(): String? {
        val serial = try {
            Build.getSerial()
        } catch (_: SecurityException) {
            null
        }
        return serial?.takeIf { isValid(it) }
    }

    private fun fromDebugOverride(context: Context): String? {
        return debugOverride(context).takeIf { isValid(it) }
    }
}
