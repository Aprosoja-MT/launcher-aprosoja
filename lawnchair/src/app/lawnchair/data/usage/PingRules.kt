package app.lawnchair.data.usage

object PingRules {
    const val MOVE_RADIUS_M = 50f
    const val IDLE_RADIUS_M = 40f
    const val IDLE_CONFIRM_MS = 420_000L
    const val MOVING_SPEED_MPS = 2f
    const val MAX_ACCURACY_M = 50f
    const val TRACK_DISTANCE_M = 150f
    const val TRACK_BEARING_DEG = 15f
    const val SYNC_MINUTES = 16L
    const val FALLBACK_PING_MINUTES = 15L
    const val COLLECT_MINUTES = 15L
    const val COLLECT_THROTTLE_MINUTES = 5L
}
