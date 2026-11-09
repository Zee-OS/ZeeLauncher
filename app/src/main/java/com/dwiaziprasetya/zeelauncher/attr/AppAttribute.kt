package com.dwiaziprasetya.zeelauncher.attr

import com.zeelauncher.core_common.constant.FOUR
import com.zeelauncher.core_designsystem.theme.Dimen

internal object AppAttribute {

    object MainActivity {
        const val SPLASH_TIMEOUT_MILLIS = 3_000L
    }

    object StatusDrawer {
        const val BATTERY_LOW_THRESHOLD = 20
        val DateSpacing = Dimen.Dp16
        val SectionSpacing = Dimen.Dp40
        val CardSpacing = Dimen.Dp20
        val CardHeight = Dimen.Dp150
        val CardPadding = Dimen.Dp20
        val CardLabelSpacing = Dimen.Dp8
        val SignalBarWidth = Dimen.Dp8
        val SignalBarSpacing = Dimen.Dp5
        val SignalBarStep = Dimen.Dp6
        val BatteryBarHeight = Dimen.Dp18
    }

    object Network {
        const val RSSI_EXCELLENT = -55
        const val RSSI_GOOD = -66
        const val RSSI_FAIR = -77
        const val MAX_SIGNAL_LEVEL = FOUR
    }

    object DeviceStatus {
        const val HOURS_24_PATTERN = "HH"
        const val HOURS_12_PATTERN = "h"
        const val MINUTES_PATTERN = "mm"
        const val DATE_PATTERN = "EEEE, d MMMM"
    }
}
