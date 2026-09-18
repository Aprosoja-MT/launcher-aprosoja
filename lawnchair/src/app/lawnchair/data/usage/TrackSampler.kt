package app.lawnchair.data.usage

import android.location.Location
import kotlin.math.abs

class TrackSampler {
    private var recorded: Location? = null
    private var heading: Float? = null

    fun shouldRecord(location: Location, mode: PingMode): Boolean {
        val last = recorded ?: return true
        val elapsed = LocationFix.timestampOf(location) - LocationFix.timestampOf(last)
        if (elapsed < mode.minGapMs) return false
        if (elapsed >= mode.reportIntervalMs) return true
        val distance = last.distanceTo(location)
        if (distance >= PingRules.TRACK_DISTANCE_M) return true
        if (distance < PingRules.TRACK_BEARING_MIN_DISTANCE_M) return false
        return deviation(last.bearingTo(location)) >= PingRules.TRACK_BEARING_DEG
    }

    fun accept(location: Location) {
        val last = recorded
        if (last != null && last.distanceTo(location) >= PingRules.TRACK_BEARING_MIN_DISTANCE_M) {
            heading = last.bearingTo(location)
        }
        recorded = Location(location)
    }

    fun reset() {
        recorded = null
        heading = null
    }

    private fun deviation(bearing: Float): Float {
        val previous = heading ?: return 0f
        val delta = abs(bearing - previous) % 360f
        return if (delta > 180f) 360f - delta else delta
    }
}
