package com.zeelauncher.core_designsystem.attr

import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.text.TextStyle
import com.zeelauncher.core_designsystem.theme.Rajdhani
import com.zeelauncher.core_designsystem.theme.ZeeDarkColors

internal object DesignSystemAttribute {

    object SoundPlayer {
        const val MAX_STREAMS = 4
    }

    object Type {
        val Base = TextStyle(fontFamily = Rajdhani)
    }

    object Theme {
        val MaterialColors = darkColorScheme(
            primary = ZeeDarkColors.accent,
            onPrimary = ZeeDarkColors.onAccent,
            background = ZeeDarkColors.background,
            onBackground = ZeeDarkColors.content,
            surface = ZeeDarkColors.background,
            onSurface = ZeeDarkColors.content,
            onSurfaceVariant = ZeeDarkColors.contentSecondary,
            outline = ZeeDarkColors.content,
        )
    }

    object HintRow {
        const val LABEL_ANIMATION = "hint_label"
    }

    object Drawer {
        const val SLIDE_LABEL = "drawer_slide"
    }

    object AppGrid {
        const val TILE_FOCUS_LABEL = "app_grid_tile_focus"
    }

    object Toast {
        const val DURATION_MILLIS = 3_000L
        const val MAX_VISIBLE = 3
    }

    object Icons {
        const val VIEWPORT = 22f
        const val WIFI_CENTER_X = 11f
        const val WIFI_CENTER_Y = 19f
        const val WIFI_RADIUS_SMALL = 4.5f
        const val WIFI_RADIUS_MEDIUM = 8.5f
        const val WIFI_RADIUS_LARGE = 12.5f
        const val WIFI_START_ANGLE = 225f
        const val WIFI_SWEEP_ANGLE = 90f
        const val WIFI_DOT_RADIUS = 1.6f
        const val BATTERY_LOW_THRESHOLD = 20
        const val SIGNAL_BAR_COUNT = 4
        const val SIGNAL_LEFT = 3f
        const val SIGNAL_BOTTOM = 19f
        const val SIGNAL_BAR_WIDTH = 3f
        const val SIGNAL_BAR_GAP = 1.5f
        const val SIGNAL_BAR_STEP = 3.5f
        const val CHECK_START_X = 4f
        const val CHECK_START_Y = 11.5f
        const val CHECK_MIDDLE_X = 9f
        const val CHECK_MIDDLE_Y = 16.5f
        const val CHECK_END_X = 18f
        const val CHECK_END_Y = 6f
        const val CLOSE_MIN = 5f
        const val CLOSE_MAX = 17f
        const val CONTROLLER_LEFT = 2f
        const val CONTROLLER_TOP = 6f
        const val CONTROLLER_RIGHT = 20f
        const val CONTROLLER_BOTTOM = 16f
        const val CONTROLLER_DPAD_X = 7f
        const val CONTROLLER_DPAD_Y = 11f
        const val CONTROLLER_DPAD_ARM = 2f
        const val CONTROLLER_BUTTON_A_X = 14f
        const val CONTROLLER_BUTTON_A_Y = 10f
        const val CONTROLLER_BUTTON_B_X = 16.5f
        const val CONTROLLER_BUTTON_B_Y = 12.5f
        const val CONTROLLER_BUTTON_RADIUS = 1.3f
        const val SLASH_MIN = 2f
        const val SLASH_MAX = 20f
    }
}
