package com.zeelauncher.feature_library.viewmodel

import com.zeelauncher.core_common.constant.ONE
import com.zeelauncher.core_common.constant.ZERO_LONG
import com.zeelauncher.feature_library.attr.LibraryFeatureAttribute
import java.util.concurrent.TimeUnit

sealed interface RelativeTime {
    data object Never : RelativeTime
    data object JustNow : RelativeTime
    data class Minutes(val count: Int) : RelativeTime
    data class Hours(val count: Int) : RelativeTime
    data object Yesterday : RelativeTime
    data class Days(val count: Int) : RelativeTime
    data class Months(val count: Int) : RelativeTime
    data class Years(val count: Int) : RelativeTime
}

internal fun relativeTimeOf(timestamp: Long?, now: Long): RelativeTime {
    if (timestamp == null) return RelativeTime.Never
    val elapsed = (now - timestamp).coerceAtLeast(ZERO_LONG)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(elapsed)
    val hours = TimeUnit.MILLISECONDS.toHours(elapsed)
    val days = TimeUnit.MILLISECONDS.toDays(elapsed)
    return when {
        minutes < ONE -> RelativeTime.JustNow
        hours < ONE -> RelativeTime.Minutes(minutes.toInt())
        days < ONE -> RelativeTime.Hours(hours.toInt())
        days == ONE.toLong() -> RelativeTime.Yesterday
        days < LibraryFeatureAttribute.RelativeTime.DAYS_PER_MONTH -> RelativeTime.Days(days.toInt())
        days < LibraryFeatureAttribute.RelativeTime.DAYS_PER_YEAR ->
            RelativeTime.Months((days / LibraryFeatureAttribute.RelativeTime.DAYS_PER_MONTH).toInt())
        else -> RelativeTime.Years((days / LibraryFeatureAttribute.RelativeTime.DAYS_PER_YEAR).toInt())
    }
}
