package app.lawnchair.data.usage

import kotlin.random.Random
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class UsageSweepTest {
    private val dayOne = UsageDayWindow(date = "2026-01-01", startMs = 1000L, endMs = 2000L)
    private val dayTwo = UsageDayWindow(date = "2026-01-02", startMs = 2000L, endMs = 3000L)
    private val days = listOf(dayOne, dayTwo)

    private fun sweep(
        events: List<UsageEvent>,
        screenInteractiveAtEnd: Boolean? = null,
    ): Map<String, UsageDaySweep> = UsageSweep.run(events, days, 0L, 3000L, screenInteractiveAtEnd).associateBy { it.date }

    private fun screenOn(at: Long) = UsageEvent(at, UsageEventType.SCREEN_INTERACTIVE)

    private fun screenOff(at: Long) = UsageEvent(at, UsageEventType.SCREEN_NON_INTERACTIVE)

    private fun keyguardShown(at: Long) = UsageEvent(at, UsageEventType.KEYGUARD_SHOWN)

    private fun keyguardHidden(at: Long) = UsageEvent(at, UsageEventType.KEYGUARD_HIDDEN)

    private fun resumed(at: Long, packageName: String, className: String? = "$packageName.Main") = UsageEvent(at, UsageEventType.ACTIVITY_RESUMED, packageName, className)

    private fun paused(at: Long, packageName: String, className: String? = "$packageName.Main") = UsageEvent(at, UsageEventType.ACTIVITY_PAUSED, packageName, className)

    private fun stopped(at: Long, packageName: String, className: String? = "$packageName.Main") = UsageEvent(at, UsageEventType.ACTIVITY_STOPPED, packageName, className)

    private fun shutdown(at: Long) = UsageEvent(at, UsageEventType.DEVICE_SHUTDOWN)

    private fun startup(at: Long) = UsageEvent(at, UsageEventType.DEVICE_STARTUP)

    private fun UsageDaySweep.foregroundOf(packageName: String): Long = packages[packageName]?.foregroundMs ?: 0L

    private fun UsageDaySweep.opensOf(packageName: String): Int = packages[packageName]?.openCount ?: 0

    @Test
    fun screenOnBeforeRangeStartIsCreditedFromDayStart() {
        val result = sweep(listOf(screenOn(500L), screenOff(1500L)))

        assertEquals(500L, result.getValue("2026-01-01").screenOnMs)
        assertEquals(0L, result.getValue("2026-01-02").screenOnMs)
    }

    @Test
    fun screenOnSessionSpanningMidnightSplitsAcrossBothDays() {
        val result = sweep(listOf(screenOn(1500L), screenOff(2500L)))

        assertEquals(500L, result.getValue("2026-01-01").screenOnMs)
        assertEquals(500L, result.getValue("2026-01-02").screenOnMs)
    }

    @Test
    fun screenOnSessionSpanningEntireDaySplitsCorrectly() {
        val result = sweep(listOf(screenOn(500L), screenOff(2900L)))

        assertEquals(1000L, result.getValue("2026-01-01").screenOnMs)
        assertEquals(900L, result.getValue("2026-01-02").screenOnMs)
    }

    @Test
    fun openSessionAtWindowEndIsCreditedToWindowEnd() {
        val result = sweep(listOf(screenOn(2500L)))

        assertEquals(0L, result.getValue("2026-01-01").screenOnMs)
        assertEquals(500L, result.getValue("2026-01-02").screenOnMs)
    }

    @Test
    fun foregroundIntervalSpanningMidnightSplitsAcrossBothDays() {
        val result = sweep(listOf(screenOn(1000L), resumed(1500L, "a"), screenOff(2500L)))

        assertEquals(500L, result.getValue("2026-01-01").foregroundOf("a"))
        assertEquals(500L, result.getValue("2026-01-02").foregroundOf("a"))
        assertEquals(1, result.getValue("2026-01-01").opensOf("a"))
        assertEquals(0, result.getValue("2026-01-02").opensOf("a"))
    }

    @Test
    fun activityBeforeRangeStartContributesOnlyOverlap() {
        val result = sweep(listOf(screenOn(200L), resumed(200L, "a"), paused(1200L, "a")))

        assertEquals(200L, result.getValue("2026-01-01").foregroundOf("a"))
    }

    @Test
    fun eventsEntirelyInLookbackContributeNothing() {
        val result = sweep(
            listOf(screenOn(100L), resumed(200L, "a"), paused(300L, "a"), screenOff(400L)),
        )

        assertEquals(0L, result.getValue("2026-01-01").screenOnMs)
        assertEquals(0L, result.getValue("2026-01-02").screenOnMs)
        assertFalse(result.getValue("2026-01-01").sawScreenEvent)
        assertFalse(result.getValue("2026-01-02").sawScreenEvent)
    }

    @Test
    fun foregroundTimeWhileScreenOffIsNotCounted() {
        val result = sweep(
            listOf(
                screenOn(1000L),
                resumed(1100L, "a"),
                screenOff(1200L),
                screenOn(1800L),
                screenOff(2000L),
            ),
        )

        val day = result.getValue("2026-01-01")
        assertEquals(400L, day.screenOnMs)
        assertEquals(300L, day.foregroundOf("a"))
        assertEquals(1, day.opensOf("a"))
    }

    @Test
    fun foregroundTimeWhileKeyguardShownIsNotCounted() {
        val result = sweep(
            listOf(
                screenOn(1000L),
                resumed(1000L, "a"),
                keyguardShown(1300L),
                keyguardHidden(1600L),
                screenOff(1800L),
            ),
        )

        val day = result.getValue("2026-01-01")
        assertEquals(800L, day.screenOnMs)
        assertEquals(500L, day.foregroundOf("a"))
    }

    @Test
    fun overlappingResumesAttributeToMostRecentPackageOnly() {
        val result = sweep(
            listOf(screenOn(1000L), resumed(1200L, "a"), resumed(1600L, "b"), screenOff(1800L)),
        )

        val day = result.getValue("2026-01-01")
        assertEquals(800L, day.screenOnMs)
        assertEquals(400L, day.foregroundOf("a"))
        assertEquals(200L, day.foregroundOf("b"))
    }

    @Test
    fun sumOfPackageTimeNeverExceedsScreenOnTime() {
        val random = Random(20260918)
        val types = UsageEventType.entries
        val packages = listOf("a", "b", "c")
        repeat(50) {
            val events = List(200) {
                val type = types[random.nextInt(types.size)]
                UsageEvent(
                    timestamp = random.nextLong(0L, 3000L),
                    type = type,
                    packageName = if (type.requiresPackage) packages[random.nextInt(packages.size)] else null,
                    className = if (type.requiresPackage) "cls${random.nextInt(2)}" else null,
                )
            }
            for (day in UsageSweep.run(events, days, 0L, 3000L, null)) {
                assertTrue(day.packages.values.sumOf { it.foregroundMs } <= day.screenOnMs)
            }
        }
    }

    @Test
    fun yesterdayAndTodayAreBothComputedInOneSweep() {
        val result = sweep(
            listOf(
                screenOn(1000L),
                resumed(1100L, "a"),
                screenOff(1900L),
                screenOn(2100L),
                resumed(2200L, "b"),
                screenOff(2900L),
            ),
        )

        val first = result.getValue("2026-01-01")
        val second = result.getValue("2026-01-02")
        assertEquals(900L, first.screenOnMs)
        assertEquals(800L, first.foregroundOf("a"))
        assertEquals(800L, second.screenOnMs)
        assertEquals(700L, second.foregroundOf("b"))
    }

    @Test
    fun dayWithNoScreenEventsIsFlaggedNotSeen() {
        val result = sweep(
            listOf(resumed(1500L, "a"), paused(1700L, "a"), screenOn(2100L), screenOff(2500L)),
        )

        val first = result.getValue("2026-01-01")
        assertFalse(first.sawScreenEvent)
        assertEquals(0L, first.screenOnMs)
        assertTrue(result.getValue("2026-01-02").sawScreenEvent)
    }

    @Test
    fun emptyDayWithoutStaleAppUsageIsNotPersisted() {
        assertFalse(emptyDay().shouldPersist(hasStaleAppUsage = false))
    }

    @Test
    fun emptyDayWithStaleAppUsageIsPersisted() {
        assertTrue(emptyDay().shouldPersist(hasStaleAppUsage = true))
    }

    @Test
    fun dayWithScreenEvidenceIsPersistedWithoutStaleAppUsage() {
        assertTrue(emptyDay(sawScreenEvent = true).shouldPersist(hasStaleAppUsage = false))
    }

    @Test
    fun dayWithInferredScreenOnIsPersistedWithoutStaleAppUsage() {
        assertTrue(emptyDay(screenOnMs = 500L).shouldPersist(hasStaleAppUsage = false))
    }

    private fun emptyDay(
        screenOnMs: Long = 0L,
        sawScreenEvent: Boolean = false,
    ) = UsageDaySweep(
        date = "2026-01-01",
        screenOnMs = screenOnMs,
        sawScreenEvent = sawScreenEvent,
        packages = emptyMap(),
    )

    @Test
    fun screenOffAndOnWithSameAppDoesNotIncrementOpenCount() {
        val result = sweep(
            listOf(
                screenOn(1000L),
                resumed(1100L, "a"),
                paused(1200L, "a"),
                screenOff(1200L),
                screenOn(1500L),
                resumed(1500L, "a"),
            ),
        )

        assertEquals(1, result.getValue("2026-01-01").opensOf("a"))
    }

    @Test
    fun keyguardCycleWithSameAppDoesNotIncrementOpenCount() {
        val result = sweep(
            listOf(
                screenOn(1000L),
                resumed(1000L, "a"),
                keyguardShown(1200L),
                keyguardHidden(1400L),
            ),
        )

        assertEquals(1, result.getValue("2026-01-01").opensOf("a"))
    }

    @Test
    fun switchingBetweenAppsIncrementsEachOpenCount() {
        val result = sweep(
            listOf(screenOn(1000L), resumed(1100L, "a"), resumed(1200L, "b"), resumed(1300L, "a")),
        )

        val day = result.getValue("2026-01-01")
        assertEquals(2, day.opensOf("a"))
        assertEquals(1, day.opensOf("b"))
    }

    @Test
    fun repeatedResumeOfSameActivityDoesNotIncrementOpenCount() {
        val result = sweep(
            listOf(screenOn(1000L), resumed(1100L, "a"), resumed(1200L, "a"), resumed(1300L, "a")),
        )

        assertEquals(1, result.getValue("2026-01-01").opensOf("a"))
    }

    @Test
    fun unlockingIntoDifferentAppIncrementsOpenCount() {
        val result = sweep(
            listOf(
                screenOn(1000L),
                resumed(1000L, "a"),
                keyguardShown(1100L),
                resumed(1200L, "b"),
                keyguardHidden(1300L),
            ),
        )

        assertEquals(1, result.getValue("2026-01-01").opensOf("b"))
    }

    @Test
    fun wakingIntoDifferentAppIncrementsOpenCount() {
        val result = sweep(
            listOf(
                screenOn(1000L),
                resumed(1000L, "a"),
                screenOff(1100L),
                resumed(1200L, "b"),
                screenOn(1300L),
            ),
        )

        assertEquals(1, result.getValue("2026-01-01").opensOf("b"))
    }

    @Test
    fun openCountIsNotCountedForEventsInLookback() {
        val result = sweep(listOf(screenOn(100L), resumed(500L, "a"), screenOff(600L)))

        assertNull(result.getValue("2026-01-01").packages["a"])
    }

    @Test
    fun rebootResetsLastVisiblePackageSoFirstResumeCounts() {
        val result = sweep(
            listOf(
                screenOn(1000L),
                resumed(1000L, "a"),
                shutdown(1100L),
                startup(1200L),
                screenOn(1300L),
                resumed(1300L, "a"),
            ),
        )

        assertEquals(2, result.getValue("2026-01-01").opensOf("a"))
    }

    @Test
    fun activityStoppedForDifferentActivityOfSamePackageDoesNotClearForeground() {
        val result = sweep(
            listOf(
                screenOn(1000L),
                resumed(1100L, "x", "x.List"),
                paused(1200L, "x", "x.List"),
                resumed(1200L, "x", "x.Detail"),
                stopped(1300L, "x", "x.List"),
                screenOff(1800L),
            ),
        )

        assertEquals(700L, result.getValue("2026-01-01").foregroundOf("x"))
    }

    @Test
    fun activityStoppedWithNullClassNameDoesNotClearForeground() {
        val result = sweep(
            listOf(screenOn(1000L), resumed(1100L, "a"), stopped(1200L, "a", null), screenOff(1500L)),
        )

        assertEquals(400L, result.getValue("2026-01-01").foregroundOf("a"))
    }

    @Test
    fun activityPausedWithNullClassNameClearsForeground() {
        val result = sweep(
            listOf(screenOn(1000L), resumed(1100L, "a"), paused(1200L, "a", null), screenOff(1500L)),
        )

        assertEquals(100L, result.getValue("2026-01-01").foregroundOf("a"))
    }

    @Test
    fun activityStoppedForPreviousAppDoesNotAffectCurrentApp() {
        val result = sweep(
            listOf(
                screenOn(1000L),
                resumed(1100L, "a"),
                paused(1200L, "a"),
                resumed(1200L, "b"),
                stopped(1300L, "a"),
                screenOff(1800L),
            ),
        )

        assertEquals(600L, result.getValue("2026-01-01").foregroundOf("b"))
    }

    @Test
    fun nullPackageActivityEventsAreIgnored() {
        val result = sweep(
            listOf(
                screenOn(1000L),
                UsageEvent(1100L, UsageEventType.ACTIVITY_RESUMED, null, null),
                screenOff(1500L),
            ),
        )

        val day = result.getValue("2026-01-01")
        assertEquals(500L, day.screenOnMs)
        assertTrue(day.packages.isEmpty())
    }

    @Test
    fun firstScreenEventNonInteractiveInfersScreenWasOn() {
        val result = sweep(listOf(screenOff(1500L)))

        assertEquals(500L, result.getValue("2026-01-01").screenOnMs)
    }

    @Test
    fun inferredScreenOnIsCappedAtThirtyMinutes() {
        val day = UsageDayWindow(date = "2026-01-01", startMs = 0L, endMs = 10_000_000L)
        val result = UsageSweep.run(
            listOf(UsageEvent(5_000_000L, UsageEventType.SCREEN_NON_INTERACTIVE)),
            listOf(day),
            0L,
            10_000_000L,
            null,
        )

        assertEquals(UsageSweep.MAX_INFERRED_SCREEN_ON_MS, result.single().screenOnMs)
    }

    @Test
    fun powerManagerReportingScreenOffTruncatesTail() {
        val result = sweep(listOf(screenOn(2500L)), screenInteractiveAtEnd = false)

        assertEquals(0L, result.getValue("2026-01-02").screenOnMs)
    }

    @Test
    fun powerManagerReportingScreenOnDoesNotResurrectTail() {
        val result = sweep(listOf(screenOn(1000L), screenOff(1500L)), screenInteractiveAtEnd = true)

        assertEquals(500L, result.getValue("2026-01-01").screenOnMs)
        assertEquals(0L, result.getValue("2026-01-02").screenOnMs)
    }

    @Test
    fun nullPowerManagerLeavesTailUnchanged() {
        val result = sweep(listOf(screenOn(2500L)), screenInteractiveAtEnd = null)

        assertEquals(500L, result.getValue("2026-01-02").screenOnMs)
    }

    @Test
    fun deviceStartupWithoutShutdownDropsTheDeadInterval() {
        val result = sweep(listOf(screenOn(1100L), startup(2900L)))

        assertEquals(0L, result.getValue("2026-01-01").screenOnMs)
        assertEquals(0L, result.getValue("2026-01-02").screenOnMs)
    }

    @Test
    fun deviceShutdownStopsCreditingImmediately() {
        val result = sweep(listOf(screenOn(1000L), shutdown(1500L)))

        assertEquals(500L, result.getValue("2026-01-01").screenOnMs)
        assertEquals(0L, result.getValue("2026-01-02").screenOnMs)
    }

    @Test
    fun outOfOrderTimestampsAreSortedAndClamped() {
        val shuffled = sweep(listOf(screenOff(2500L), screenOn(1500L)))
        val ordered = sweep(listOf(screenOn(1500L), screenOff(2500L)))

        assertEquals(ordered.getValue("2026-01-01").screenOnMs, shuffled.getValue("2026-01-01").screenOnMs)
        assertEquals(ordered.getValue("2026-01-02").screenOnMs, shuffled.getValue("2026-01-02").screenOnMs)
    }

    @Test
    fun timestampsOutsideWindowAreClamped() {
        val result = sweep(listOf(screenOn(-500L), screenOff(5000L)))

        assertEquals(1000L, result.getValue("2026-01-01").screenOnMs)
        assertEquals(1000L, result.getValue("2026-01-02").screenOnMs)
    }

    @Test
    fun emptyEventListProducesZeroedDaysWithNoScreenEvidence() {
        val result = sweep(emptyList())

        for (day in result.values) {
            assertEquals(0L, day.screenOnMs)
            assertFalse(day.sawScreenEvent)
            assertTrue(day.packages.isEmpty())
        }
    }
}
