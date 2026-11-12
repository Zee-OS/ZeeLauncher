package com.zeelauncher.feature_settings.attr

import com.zeelauncher.core_designsystem.theme.Dimen

internal object SettingsFeatureAttribute {

    object Panel {
        val Padding = Dimen.Dp20
        val RowHeight = Dimen.Dp72
        val RowPaddingHorizontal = Dimen.Dp24
        val ControlSpacing = Dimen.Dp24
        val DividerThickness = Dimen.Dp2
    }

    object LicensesDialog {
        val Width = Dimen.Dp510
        val TitleSpacing = Dimen.Dp22
        val LinkSpacing = Dimen.Dp18
        val SectionSpacing = Dimen.Dp36
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
        const val PANEL_WIDTH_FRACTION = 0.6f
    }

    object ViewModel {
        const val EFFECT_BUFFER_CAPACITY = 4
    }
}
