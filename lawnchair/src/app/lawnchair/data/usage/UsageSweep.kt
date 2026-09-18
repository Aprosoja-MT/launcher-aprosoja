package app.lawnchair.data.usage

object UsageSweep {
    const val MAX_INFERRED_SCREEN_ON_MS = 30 * 60 * 1000L

    fun run(
        events: List<UsageEvent>,
        days: List<UsageDayWindow>,
        windowStartMs: Long,
        windowEndMs: Long,
        screenInteractiveAtEnd: Boolean?,
    ): List<UsageDaySweep> {
        val buckets = days.map { DayBucket(it) }
        val state = SweepState()
        var cursor = windowStartMs
        var lastEventAt = windowStartMs

        for (event in events.sortedBy { it.timestamp }) {
            val at = event.timestamp
                .coerceIn(windowStartMs, windowEndMs)
                .coerceAtLeast(cursor)

            if (event.type == UsageEventType.SCREEN_NON_INTERACTIVE && !state.screenKnown) {
                val inferredFrom = maxOf(cursor, at - MAX_INFERRED_SCREEN_ON_MS)
                credit(buckets, state, cursor, inferredFrom)
                cursor = inferredFrom
                state.screenOn = true
            }

            val flushEnd = if (event.type == UsageEventType.DEVICE_STARTUP) {
                minOf(at, lastEventAt).coerceAtLeast(cursor)
            } else {
                at
            }

            credit(buckets, state, cursor, flushEnd)
            apply(state, buckets, event, at)
            cursor = at
            lastEventAt = at
        }

        if (screenInteractiveAtEnd == false) {
            state.screenOn = false
        }
        credit(buckets, state, cursor, windowEndMs)

        return buckets.map { it.toResult() }
    }

    private fun credit(
        buckets: List<DayBucket>,
        state: SweepState,
        from: Long,
        to: Long,
    ) {
        if (to <= from || !state.screenOn) return
        val packageName = if (state.keyguardShown) null else state.foregroundPackage
        for (bucket in buckets) {
            val start = maxOf(from, bucket.window.startMs)
            val end = minOf(to, bucket.window.endMs)
            if (end <= start) continue
            val span = end - start
            bucket.screenOnMs += span
            if (packageName != null) {
                bucket.foregroundMs[packageName] = (bucket.foregroundMs[packageName] ?: 0L) + span
            }
        }
    }

    private fun apply(
        state: SweepState,
        buckets: List<DayBucket>,
        event: UsageEvent,
        at: Long,
    ) {
        when (event.type) {
            UsageEventType.SCREEN_INTERACTIVE -> {
                state.screenOn = true
                state.screenKnown = true
                markScreenEvidence(buckets, at)
                promote(state, buckets, at)
            }

            UsageEventType.SCREEN_NON_INTERACTIVE -> {
                state.screenOn = false
                state.screenKnown = true
                markScreenEvidence(buckets, at)
            }

            UsageEventType.KEYGUARD_SHOWN -> {
                state.keyguardShown = true
            }

            UsageEventType.KEYGUARD_HIDDEN -> {
                state.keyguardShown = false
                promote(state, buckets, at)
            }

            UsageEventType.ACTIVITY_RESUMED -> {
                val packageName = event.packageName ?: return
                state.foregroundPackage = packageName
                state.foregroundClass = event.className
                promote(state, buckets, at)
            }

            UsageEventType.ACTIVITY_PAUSED -> {
                val packageName = event.packageName ?: return
                if (state.holds(packageName, event.className, strict = false)) {
                    state.clearForeground()
                }
            }

            UsageEventType.ACTIVITY_STOPPED -> {
                val packageName = event.packageName ?: return
                if (state.holds(packageName, event.className, strict = true)) {
                    state.clearForeground()
                }
            }

            UsageEventType.DEVICE_SHUTDOWN -> {
                state.screenOn = false
                state.screenKnown = true
                state.keyguardShown = true
                state.clearForeground()
                state.lastVisiblePackage = null
                markScreenEvidence(buckets, at)
            }

            UsageEventType.DEVICE_STARTUP -> {
                state.screenOn = false
                state.screenKnown = false
                state.keyguardShown = false
                state.clearForeground()
                state.lastVisiblePackage = null
                markScreenEvidence(buckets, at)
            }
        }
    }

    private fun promote(state: SweepState, buckets: List<DayBucket>, at: Long) {
        if (!state.screenOn || state.keyguardShown) return
        val packageName = state.foregroundPackage ?: return
        if (packageName == state.lastVisiblePackage) return
        state.lastVisiblePackage = packageName
        for (bucket in buckets) {
            if (at >= bucket.window.startMs && at < bucket.window.endMs) {
                bucket.openCounts[packageName] = (bucket.openCounts[packageName] ?: 0) + 1
            }
        }
    }

    private fun markScreenEvidence(buckets: List<DayBucket>, at: Long) {
        for (bucket in buckets) {
            if (at >= bucket.window.startMs && at < bucket.window.endMs) {
                bucket.sawScreenEvent = true
            }
        }
    }

    private class SweepState {
        var screenOn = false
        var screenKnown = false
        var keyguardShown = false
        var foregroundPackage: String? = null
        var foregroundClass: String? = null
        var lastVisiblePackage: String? = null

        fun clearForeground() {
            foregroundPackage = null
            foregroundClass = null
        }

        fun holds(packageName: String, className: String?, strict: Boolean): Boolean {
            if (foregroundPackage != packageName) return false
            if (className == null || foregroundClass == null) return !strict
            return foregroundClass == className
        }
    }

    private class DayBucket(val window: UsageDayWindow) {
        var screenOnMs = 0L
        var sawScreenEvent = false
        val foregroundMs = mutableMapOf<String, Long>()
        val openCounts = mutableMapOf<String, Int>()

        fun toResult(): UsageDaySweep {
            val keys = foregroundMs.keys + openCounts.keys
            return UsageDaySweep(
                date = window.date,
                screenOnMs = screenOnMs,
                sawScreenEvent = sawScreenEvent,
                packages = keys.associateWith { packageName ->
                    UsagePackageUsage(
                        foregroundMs = foregroundMs[packageName] ?: 0L,
                        openCount = openCounts[packageName] ?: 0,
                    )
                },
            )
        }
    }
}
