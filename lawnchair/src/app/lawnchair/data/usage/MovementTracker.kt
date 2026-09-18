package app.lawnchair.data.usage

import android.content.Context
import android.location.Location
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MovementTracker(
    context: Context,
    private val scope: CoroutineScope,
    private val onModeChanged: (PingMode, Location) -> Unit,
) {
    private val context = context.applicationContext
    private val detector = MotionDetector(this.context) { onMotionTrigger() }
    private var currentMode = MovementState.mode(this.context)
    private var anchor: Location? = MovementState.anchor(this.context)
    private var idleCandidate: Location? = MovementState.candidate(this.context)

    val mode: PingMode get() = currentMode

    fun start() {
        if (currentMode == PingMode.IDLE) {
            detector.arm()
        }
    }

    fun stop() {
        detector.stop()
    }

    fun onLocation(location: Location) {
        if (!LocationFix.isRecordable(location)) return
        when (currentMode) {
            PingMode.IDLE -> evaluateIdle(location)
            PingMode.MOVING -> evaluateMoving(location)
        }
    }

    private fun onMotionTrigger() {
        scope.launch {
            val location = LocationFix.resolve(context)
            if (location != null) {
                onLocation(location)
            }
            if (currentMode == PingMode.IDLE) {
                detector.arm()
            }
        }
    }

    private fun evaluateIdle(location: Location) {
        val current = anchor
        if (current == null) {
            anchor = location
            MovementState.setAnchor(context, location)
            return
        }
        if (current.distanceTo(location) <= departureRadius(location)) return
        enterMoving(location)
    }

    private fun evaluateMoving(location: Location) {
        val candidate = idleCandidate
        if (isUnderWay(location) || candidate == null ||
            candidate.distanceTo(location) > PingRules.IDLE_RADIUS_M
        ) {
            setIdleCandidate(location)
            return
        }
        if (LocationFix.timestampOf(location) - candidate.time < PingRules.IDLE_CONFIRM_MS) return
        enterIdle(location)
    }

    private fun enterMoving(location: Location) {
        currentMode = PingMode.MOVING
        MovementState.setMode(context, PingMode.MOVING)
        anchor = null
        MovementState.clearAnchor(context)
        setIdleCandidate(location)
        detector.stop()
        onModeChanged(PingMode.MOVING, location)
    }

    private fun enterIdle(location: Location) {
        val resting = idleCandidate ?: location
        currentMode = PingMode.IDLE
        MovementState.setMode(context, PingMode.IDLE)
        anchor = resting
        MovementState.setAnchor(context, resting)
        idleCandidate = null
        MovementState.clearCandidate(context)
        detector.arm()
        onModeChanged(PingMode.IDLE, location)
    }

    private fun setIdleCandidate(location: Location) {
        val candidate = Location(location).apply { time = LocationFix.timestampOf(location) }
        idleCandidate = candidate
        MovementState.setCandidate(context, candidate)
    }

    private fun isUnderWay(location: Location): Boolean {
        return location.hasSpeed() && location.speed >= PingRules.MOVING_SPEED_MPS
    }

    private fun departureRadius(location: Location): Float {
        val accuracy = if (location.hasAccuracy()) location.accuracy else 0f
        return PingRules.MOVE_RADIUS_M + accuracy
    }
}
