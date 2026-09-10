package app.lawnchair.data.usage

import android.content.Context

class LauncherAuthStore(context: Context) {
    private val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    fun token(): String? = prefs.getString(TOKEN, null)?.takeIf { it.isNotBlank() }

    fun tabletId(): String? = prefs.getString(TABLET_ID, null)?.takeIf { it.isNotBlank() }

    fun lastSyncedPingId(): Long = prefs.getLong(LAST_PING_ID, 0L)

    fun lastSyncAt(): Long = prefs.getLong(LAST_SYNC_AT, 0L)

    fun saveToken(tabletId: String, token: String) {
        prefs.edit()
            .putString(TABLET_ID, tabletId)
            .putString(TOKEN, token)
            .apply()
    }

    fun clearToken() {
        prefs.edit()
            .remove(TOKEN)
            .remove(TABLET_ID)
            .apply()
    }

    fun markSynced(pingId: Long) {
        prefs.edit()
            .putLong(LAST_PING_ID, pingId)
            .putLong(LAST_SYNC_AT, System.currentTimeMillis())
            .apply()
    }

    companion object {
        private const val PREFS = "usage_audit"
        private const val TOKEN = "launcher_token"
        private const val TABLET_ID = "launcher_token_tablet"
        private const val LAST_PING_ID = "launcher_last_ping_id"
        private const val LAST_SYNC_AT = "launcher_last_sync_at"
    }
}
