package app.lawnchair.data.usage

import android.content.Context
import com.android.launcher3.BuildConfig

object LauncherApiConfig {
    const val URL_RESTRICTION_KEY = "launcher_api_base_url"
    const val SECRET_RESTRICTION_KEY = "launcher_bootstrap_secret"
    const val USERNAME_RESTRICTION_KEY = "username"

    private const val DEBUG_PREFS = "usage_audit"
    private const val DEBUG_URL_KEY = "debug_launcher_api_url"
    private const val DEBUG_SECRET_KEY = "debug_launcher_bootstrap"
    private const val DEBUG_USERNAME_KEY = "debug_launcher_username"

    fun baseUrl(context: Context): String? {
        return normalizeUrl(fromRestrictions(context, URL_RESTRICTION_KEY))
            ?: normalizeUrl(debugUrl(context))
    }

    fun bootstrapSecret(context: Context): String? {
        return fromRestrictions(context, SECRET_RESTRICTION_KEY)
            ?: debugSecret(context).takeIf { it.isNotBlank() }
    }

    fun username(context: Context): String? {
        return fromRestrictions(context, USERNAME_RESTRICTION_KEY)
            ?: debugUsername(context).takeIf { it.isNotBlank() }
    }

    fun isConfigured(context: Context): Boolean {
        return baseUrl(context) != null &&
            bootstrapSecret(context) != null &&
            username(context) != null
    }

    fun debugUrl(context: Context): String {
        if (!BuildConfig.DEBUG) return ""
        return context.getSharedPreferences(DEBUG_PREFS, Context.MODE_PRIVATE)
            .getString(DEBUG_URL_KEY, "")
            .orEmpty()
    }

    fun debugSecret(context: Context): String {
        if (!BuildConfig.DEBUG) return ""
        return context.getSharedPreferences(DEBUG_PREFS, Context.MODE_PRIVATE)
            .getString(DEBUG_SECRET_KEY, "")
            .orEmpty()
    }

    fun debugUsername(context: Context): String {
        if (!BuildConfig.DEBUG) return ""
        return context.getSharedPreferences(DEBUG_PREFS, Context.MODE_PRIVATE)
            .getString(DEBUG_USERNAME_KEY, "")
            .orEmpty()
    }

    fun setDebugUrl(context: Context, value: String) {
        if (!BuildConfig.DEBUG) return
        context.getSharedPreferences(DEBUG_PREFS, Context.MODE_PRIVATE)
            .edit()
            .putString(DEBUG_URL_KEY, value.trim())
            .apply()
    }

    fun setDebugSecret(context: Context, value: String) {
        if (!BuildConfig.DEBUG) return
        context.getSharedPreferences(DEBUG_PREFS, Context.MODE_PRIVATE)
            .edit()
            .putString(DEBUG_SECRET_KEY, value.trim())
            .apply()
    }

    fun setDebugUsername(context: Context, value: String) {
        if (!BuildConfig.DEBUG) return
        context.getSharedPreferences(DEBUG_PREFS, Context.MODE_PRIVATE)
            .edit()
            .putString(DEBUG_USERNAME_KEY, value.trim())
            .apply()
    }

    private fun fromRestrictions(context: Context, key: String): String? {
        return Restrictions.string(context, key)
    }

    private fun normalizeUrl(raw: String?): String? {
        val value = raw?.trim()?.trimEnd('/') ?: return null
        if (value.isEmpty()) return null
        return "$value/"
    }
}
