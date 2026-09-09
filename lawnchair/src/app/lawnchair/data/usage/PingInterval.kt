package app.lawnchair.data.usage

import android.content.Context
import android.content.RestrictionsManager
import com.android.launcher3.BuildConfig

enum class PingIntervalSource {
    KNOX,
    DEBUG,
    DEFAULT,
}

object PingInterval {
    const val RESTRICTION_KEY = "ping_interval_min"
    const val DEFAULT = 15
    const val MIN = 1
    const val MAX = 60
    const val ROUTE_THRESHOLD = 15

    private const val DEBUG_PREFS = "usage_audit"
    private const val DEBUG_KEY = "debug_ping_interval"

    fun resolve(context: Context): Int {
        return fromRestrictions(context)
            ?: fromDebugOverride(context)
            ?: DEFAULT
    }

    fun source(context: Context): PingIntervalSource {
        fromRestrictions(context)?.let { return PingIntervalSource.KNOX }
        fromDebugOverride(context)?.let { return PingIntervalSource.DEBUG }
        return PingIntervalSource.DEFAULT
    }

    fun isRoute(intervalMin: Int): Boolean = intervalMin < ROUTE_THRESHOLD

    fun debugOverride(context: Context): String {
        if (!BuildConfig.DEBUG) return ""
        return context.getSharedPreferences(DEBUG_PREFS, Context.MODE_PRIVATE)
            .getString(DEBUG_KEY, "")
            .orEmpty()
    }

    fun setDebugOverride(context: Context, raw: String) {
        if (!BuildConfig.DEBUG) return
        context.getSharedPreferences(DEBUG_PREFS, Context.MODE_PRIVATE)
            .edit()
            .putString(DEBUG_KEY, raw.trim())
            .apply()
    }

    private fun fromRestrictions(context: Context): Int? {
        val restrictions = context.getSystemService(RestrictionsManager::class.java)
            ?.applicationRestrictions
            ?: return null
        if (!restrictions.containsKey(RESTRICTION_KEY)) return null
        val parsed = when (val value = restrictions.get(RESTRICTION_KEY)) {
            is Int -> value
            is String -> value.toIntOrNull()
            else -> null
        }
        return parsed?.let(::clampOrNull)
    }

    private fun fromDebugOverride(context: Context): Int? {
        return debugOverride(context).toIntOrNull()?.let(::clampOrNull)
    }

    private fun clampOrNull(value: Int): Int? {
        if (value !in MIN..MAX) return null
        return value
    }
}
