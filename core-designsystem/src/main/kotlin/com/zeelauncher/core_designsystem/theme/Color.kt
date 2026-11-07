package com.zeelauncher.core_designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class ZeeColors(
    val background: Color,
    val onAccent: Color,
    val content: Color,
    val title: Color,
    val accent: Color,
    val shadow: Color,
    val contentSecondary: Color,
    val hint: Color,
    val success: Color,
    val scrim: Color,
)

val ZeeDarkColors = ZeeColors(
    background = Color(0xFF14161F),
    onAccent = Color(0xFF0B0D14),
    content = Color(0xFFF4F4EE),
    title = Color(0xFFFFFFFF),
    accent = Color(0xFFFF4D8D),
    shadow = Color(0xFF4A5272),
    contentSecondary = Color(0xFF9AA3B8),
    hint = Color(0xFFC9D0E0),
    success = Color(0xFF3DDC97),
    scrim = Color(0xBF0B0D14),
)

val LocalZeeColors = staticCompositionLocalOf { ZeeDarkColors }
