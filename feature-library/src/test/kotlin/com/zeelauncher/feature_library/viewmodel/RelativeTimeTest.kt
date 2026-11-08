package com.zeelauncher.feature_library.viewmodel

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.concurrent.TimeUnit

class RelativeTimeTest {

    private val now = TimeUnit.DAYS.toMillis(1000)

    private fun ago(duration: Long, unit: TimeUnit): Long = now - unit.toMillis(duration)

    @Test
    fun neverPlayedIsNever() {
        assertEquals(RelativeTime.Never, relativeTimeOf(null, now))
    }

    @Test
    fun underOneMinuteIsJustNow() {
        assertEquals(RelativeTime.JustNow, relativeTimeOf(ago(30, TimeUnit.SECONDS), now))
    }

    @Test
    fun futureTimestampIsJustNow() {
        assertEquals(RelativeTime.JustNow, relativeTimeOf(now + TimeUnit.MINUTES.toMillis(5), now))
    }

    @Test
    fun underOneHourIsMinutes() {
        assertEquals(RelativeTime.Minutes(5), relativeTimeOf(ago(5, TimeUnit.MINUTES), now))
    }

    @Test
    fun fiftyNineMinutesIsStillMinutes() {
        assertEquals(RelativeTime.Minutes(59), relativeTimeOf(ago(59, TimeUnit.MINUTES), now))
    }

    @Test
    fun underOneDayIsHours() {
        assertEquals(RelativeTime.Hours(3), relativeTimeOf(ago(3, TimeUnit.HOURS), now))
    }

    @Test
    fun oneDayIsYesterday() {
        assertEquals(RelativeTime.Yesterday, relativeTimeOf(ago(25, TimeUnit.HOURS), now))
    }

    @Test
    fun underThirtyDaysIsDays() {
        assertEquals(RelativeTime.Days(3), relativeTimeOf(ago(3, TimeUnit.DAYS), now))
    }

    @Test
    fun underOneYearIsMonths() {
        assertEquals(RelativeTime.Months(2), relativeTimeOf(ago(65, TimeUnit.DAYS), now))
    }

    @Test
    fun overOneYearIsYears() {
        assertEquals(RelativeTime.Years(1), relativeTimeOf(ago(400, TimeUnit.DAYS), now))
    }
}
