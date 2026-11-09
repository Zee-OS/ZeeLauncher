package com.zeelauncher.feature_settings.attr

import com.zeelauncher.core_designsystem.theme.Dimen
import com.zeelauncher.feature_settings.R

internal object SettingsFeatureAttribute {

    object Panel {
        val PaddingHorizontal = Dimen.Dp40
        val PaddingVertical = Dimen.Dp34
        val TitleSpacing = Dimen.Dp16
        val RowSpacing = Dimen.Dp8
        val RowPaddingHorizontal = Dimen.Dp20
        val RowPaddingVertical = Dimen.Dp14
        val ControlSpacing = Dimen.Dp24
        val SubtitleSpacing = Dimen.Dp4
    }

    object Sidebar {
        val ItemHeight = Dimen.Dp56
        val ItemSpacing = Dimen.Dp16
    }

    object LicensesDialog {
        val Width = Dimen.Dp510
        val EntrySpacing = Dimen.Dp14
        val SectionSpacing = Dimen.Dp30
        val LicenseEntries = listOf(
            R.string.settings_license_androidx to R.string.settings_license_apache,
            R.string.settings_license_kotlin to R.string.settings_license_apache,
            R.string.settings_license_hilt to R.string.settings_license_apache,
            R.string.settings_license_coil to R.string.settings_license_apache,
            R.string.settings_license_rajdhani to R.string.settings_license_ofl,
        )
    }

    object Controls {
        const val KNOB_LABEL = "settings_toggle_knob"
        val ToggleWidth = Dimen.Dp84
        val ToggleHeight = Dimen.Dp40
        val ToggleKnobSize = Dimen.Dp24
        val ToggleKnobInset = Dimen.Dp5
        val StatusSpacing = Dimen.Dp14
    }

    object Screen {
        val SidebarWidth = Dimen.Dp340
        val ContentSpacing = Dimen.Dp48
        val ContentPaddingTop = Dimen.Dp40
        val ContentPaddingBottom = Dimen.Dp30
    }

    object ViewModel {
        const val EFFECT_BUFFER_CAPACITY = 4
    }
}
