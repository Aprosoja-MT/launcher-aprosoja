package app.lawnchair.data.usage

import java.util.Calendar
import java.util.Locale

object UsageDates {
    fun today(): String = format(Calendar.getInstance())

    fun recentDays(count: Int, now: Long): List<UsageDayWindow> {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = now
        startOfDay(calendar)
        calendar.add(Calendar.DAY_OF_YEAR, -(count - 1))
        val windows = mutableListOf<UsageDayWindow>()
        repeat(count) {
            startOfDay(calendar)
            val startMs = calendar.timeInMillis
            val date = format(calendar)
            calendar.add(Calendar.DAY_OF_YEAR, 1)
            startOfDay(calendar)
            windows += UsageDayWindow(date = date, startMs = startMs, endMs = calendar.timeInMillis)
        }
        return windows
    }

    fun pruneCutoff(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -30)
        return format(calendar)
    }

    private fun startOfDay(calendar: Calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
    }

    private fun format(calendar: Calendar): String {
        return String.format(
            Locale.US,
            "%04d-%02d-%02d",
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH) + 1,
            calendar.get(Calendar.DAY_OF_MONTH),
        )
    }
}
