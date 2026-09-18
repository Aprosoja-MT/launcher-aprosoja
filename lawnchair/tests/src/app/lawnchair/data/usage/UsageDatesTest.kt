package app.lawnchair.data.usage

import java.util.Calendar
import java.util.TimeZone
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UsageDatesTest {
    private lateinit var original: TimeZone

    @Before
    fun captureTimeZone() {
        original = TimeZone.getDefault()
    }

    @After
    fun restoreTimeZone() {
        TimeZone.setDefault(original)
    }

    private fun millisAt(year: Int, month: Int, day: Int, hour: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, hour, 0, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

    @Test
    fun recentDaysReturnsOldestFirstWithContiguousBoundaries() {
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"))

        val days = UsageDates.recentDays(3, millisAt(2026, Calendar.JUNE, 15, 14))

        assertEquals(3, days.size)
        assertEquals("2026-06-13", days[0].date)
        assertEquals("2026-06-14", days[1].date)
        assertEquals("2026-06-15", days[2].date)
        assertEquals(days[0].endMs, days[1].startMs)
        assertEquals(days[1].endMs, days[2].startMs)
    }

    @Test
    fun recentDaysCoversTheInstantItWasBuiltFrom() {
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"))

        val now = millisAt(2026, Calendar.JUNE, 15, 14)
        val days = UsageDates.recentDays(2, now)

        assertEquals(true, now >= days.last().startMs && now < days.last().endMs)
    }

    @Test
    fun recentDaysUsesCuiabaDayBoundaries() {
        TimeZone.setDefault(TimeZone.getTimeZone("America/Cuiaba"))

        val days = UsageDates.recentDays(2, millisAt(2026, Calendar.SEPTEMBER, 18, 14))

        assertEquals("2026-09-17", days[0].date)
        assertEquals("2026-09-18", days[1].date)
        assertEquals(24 * 60 * 60 * 1000L, days[0].endMs - days[0].startMs)
        assertEquals(24 * 60 * 60 * 1000L, days[1].endMs - days[1].startMs)
        assertEquals(days[0].endMs, days[1].startMs)
    }

    @Test
    fun cuiabaDayStartsAtLocalMidnightNotUtcMidnight() {
        TimeZone.setDefault(TimeZone.getTimeZone("America/Cuiaba"))

        val days = UsageDates.recentDays(1, millisAt(2026, Calendar.SEPTEMBER, 18, 14))
        val utc = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        utc.timeInMillis = days.single().startMs

        assertEquals(2026, utc.get(Calendar.YEAR))
        assertEquals(Calendar.SEPTEMBER, utc.get(Calendar.MONTH))
        assertEquals(18, utc.get(Calendar.DAY_OF_MONTH))
        assertEquals(4, utc.get(Calendar.HOUR_OF_DAY))
    }

    @Test
    fun recentDaysHandlesDaylightSavingTransition() {
        TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"))

        val days = UsageDates.recentDays(2, millisAt(2026, Calendar.MARCH, 9, 12))

        assertEquals("2026-03-08", days[0].date)
        assertEquals("2026-03-09", days[1].date)
        assertEquals(23 * 60 * 60 * 1000L, days[0].endMs - days[0].startMs)
        assertEquals(24 * 60 * 60 * 1000L, days[1].endMs - days[1].startMs)
        assertEquals(days[0].endMs, days[1].startMs)
    }
}
