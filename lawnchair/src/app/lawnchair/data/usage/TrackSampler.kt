package app.lawnchair.data.usage

import android.location.Location
import kotlin.math.abs

class TrackSampler {
    private var recorded: Location? = null

    fun shouldRecord(location: Location, mode: PingMode): Boolean {
        val last = recorded ?: return true
        val elapsed = LocationFix.timestampOf(location) - LocationFix.timestampOf(last)
        if (elapsed < mode.minGapMs) return false
        if (elapsed >= mode.reportIntervalMs) return true
        if (last.distanceTo(location) >= PingRules.TRACK_DISTANCE_M) return true
        return turned(last, location)
    }

    fun accept(location: Location) {
        recorded = Location(location)
    }

    fun reset() {
        recorded = null
    }

    private fun turned(last: Location, location: Location): Boolean {
        if (!last.hasBearing() || !location.hasBearing()) return false
        val delta = abs(location.bearing - last.bearing) % 360f
        val shortest = if (delta > 180f) 360f - delta else delta
        return shortest >= PingRules.TRACK_BEARING_DEG
    }
}
