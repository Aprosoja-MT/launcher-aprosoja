package app.lawnchair.data.usage

import android.content.Context
import android.location.Location

object MovementState {
    private const val PREFS = "usage_audit"
    private const val MODE = "movement_mode"
    private const val ANCHOR_LAT = "movement_anchor_lat"
    private const val ANCHOR_LON = "movement_anchor_lon"
    private const val ANCHOR_PROVIDER = "movement_anchor"
    private const val CANDIDATE_LAT = "movement_candidate_lat"
    private const val CANDIDATE_LON = "movement_candidate_lon"
    private const val CANDIDATE_AT = "movement_candidate_at"
    private const val CANDIDATE_PROVIDER = "movement_candidate"

    fun mode(context: Context): PingMode = PingMode.fromName(prefs(context).getString(MODE, null))

    fun setMode(context: Context, mode: PingMode) {
        if (mode(context) == mode) return
        prefs(context).edit()
            .putString(MODE, mode.name)
            .apply()
    }

    fun anchor(context: Context): Location? = read(context, ANCHOR_LAT, ANCHOR_LON, ANCHOR_PROVIDER)

    fun setAnchor(context: Context, location: Location) {
        write(context, ANCHOR_LAT, ANCHOR_LON, location)
    }

    fun clearAnchor(context: Context) {
        clear(context, ANCHOR_LAT, ANCHOR_LON)
    }

    fun candidate(context: Context): Location? {
        val candidate = read(context, CANDIDATE_LAT, CANDIDATE_LON, CANDIDATE_PROVIDER) ?: return null
        candidate.time = prefs(context).getLong(CANDIDATE_AT, 0L)
        return candidate
    }

    fun setCandidate(context: Context, location: Location) {
        write(context, CANDIDATE_LAT, CANDIDATE_LON, location)
        prefs(context).edit()
            .putLong(CANDIDATE_AT, location.time)
            .apply()
    }

    fun clearCandidate(context: Context) {
        clear(context, CANDIDATE_LAT, CANDIDATE_LON)
        prefs(context).edit()
            .remove(CANDIDATE_AT)
            .apply()
    }

    private fun read(context: Context, latKey: String, lonKey: String, provider: String): Location? {
        val store = prefs(context)
        if (!store.contains(latKey) || !store.contains(lonKey)) return null
        return Location(provider).apply {
            latitude = Double.fromBits(store.getLong(latKey, 0L))
            longitude = Double.fromBits(store.getLong(lonKey, 0L))
        }
    }

    private fun write(context: Context, latKey: String, lonKey: String, location: Location) {
        prefs(context).edit()
            .putLong(latKey, location.latitude.toRawBits())
            .putLong(lonKey, location.longitude.toRawBits())
            .apply()
    }

    private fun clear(context: Context, latKey: String, lonKey: String) {
        prefs(context).edit()
            .remove(latKey)
            .remove(lonKey)
            .apply()
    }

    private fun prefs(context: Context) = context.applicationContext.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
}
