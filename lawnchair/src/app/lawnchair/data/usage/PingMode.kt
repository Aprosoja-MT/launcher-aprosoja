package app.lawnchair.data.usage

enum class PingMode(val intervalSec: Int) {
    IDLE(900),
    MOVING(30),
    ;

    val intervalMs: Long get() = intervalSec * 1000L

    val minGapMs: Long get() = intervalSec * 800L

    companion object {
        fun fromName(value: String?): PingMode = entries.firstOrNull { it.name == value } ?: IDLE
    }
}
