package app.lawnchair.data.usage

enum class PingMode(
    val sampleSec: Int,
    val reportSec: Int,
    val minGapSec: Int,
) {
    IDLE(60, 1800, 840),
    MOVING(5, 30, 4),
    ;

    val sampleIntervalMs: Long get() = sampleSec * 1000L

    val reportIntervalMs: Long get() = reportSec * 1000L

    val minGapMs: Long get() = minGapSec * 1000L

    companion object {
        fun fromName(value: String?): PingMode = entries.firstOrNull { it.name == value } ?: IDLE
    }
}
