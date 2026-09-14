package app.lawnchair.data.usage

import android.content.Context
import android.location.Location

object MovementState {
    private const val PREFS = "usage_audit"
    private const val MODE = "movement_mode"
    private const val ANCHOR_LAT = "movement_anchor_lat"
    private const val ANCHOR_LON = "movement_anchor_lon"
    private const val ANCHOR_PROVIDER = "movement_anchor"

    fun mode(context: Context): PingMode = PingMode.fromName(prefs(context).getString(MODE, null))

    fun setMode(context: Context, mode: PingMode) {
        if (mode(context) == mode) return
        prefs(context).edit()
            .putString(MODE, mode.name)
            .apply()
    }

    fun anchor(context: Context): Location? {
        val store = prefs(context)
        if (!store.contains(ANCHOR_LAT) || !store.contains(ANCHOR_LON)) return null
        return Location(ANCHOR_PROVIDER).apply {
            latitude = Double.fromBits(store.getLong(ANCHOR_LAT, 0L))
            longitude = Double.fromBits(store.getLong(ANCHOR_LON, 0L))
        }
    }

    fun setAnchor(context: Context, location: Location) {
        prefs(context).edit()
            .putLong(ANCHOR_LAT, location.latitude.toRawBits())
            .putLong(ANCHOR_LON, location.longitude.toRawBits())
            .apply()
    }

    fun clearAnchor(context: Context) {
        prefs(context).edit()
            .remove(ANCHOR_LAT)
            .remove(ANCHOR_LON)
            .apply()
    }

    private fun prefs(context: Context) = context.applicationContext.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
}
