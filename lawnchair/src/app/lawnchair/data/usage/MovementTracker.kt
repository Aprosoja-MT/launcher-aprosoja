package app.lawnchair.data.usage

import android.content.Context
import android.location.Location
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MovementTracker(
    context: Context,
    private val scope: CoroutineScope,
    private val onModeChanged: (PingMode) -> Unit,
) {
    private val context = context.applicationContext
    private val detector = MotionDetector(this.context) { onMotionTrigger() }
    private var currentMode = MovementState.mode(this.context)
    private var anchor: Location? = MovementState.anchor(this.context)
    private var idleCandidate: Location? = null
    private var idleFixes = 0

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
        if (!isUsable(location)) return
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
        if (current.distanceTo(location) <= PingRules.MOVE_RADIUS_M) return
        enterMoving(location)
    }

    private fun evaluateMoving(location: Location) {
        val candidate = idleCandidate
        if (candidate != null && candidate.distanceTo(location) <= PingRules.MOVE_RADIUS_M) {
            idleFixes += 1
            if (idleFixes >= PingRules.IDLE_CONFIRM_FIXES) {
                enterIdle(candidate)
            }
            return
        }
        idleCandidate = location
        idleFixes = 1
    }

    private fun enterMoving(location: Location) {
        currentMode = PingMode.MOVING
        MovementState.setMode(context, PingMode.MOVING)
        anchor = null
        MovementState.clearAnchor(context)
        idleCandidate = location
        idleFixes = 1
        detector.stop()
        onModeChanged(PingMode.MOVING)
    }

    private fun enterIdle(location: Location) {
        currentMode = PingMode.IDLE
        MovementState.setMode(context, PingMode.IDLE)
        anchor = location
        MovementState.setAnchor(context, location)
        idleCandidate = null
        idleFixes = 0
        detector.arm()
        onModeChanged(PingMode.IDLE)
    }

    private fun isUsable(location: Location): Boolean {
        if (!LocationFix.isValid(location)) return false
        return !location.hasAccuracy() || location.accuracy <= PingRules.MAX_ACCURACY_M
    }
}
