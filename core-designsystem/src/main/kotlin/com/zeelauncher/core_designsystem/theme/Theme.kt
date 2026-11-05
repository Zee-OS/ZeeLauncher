package com.zeelauncher.core_designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

private val ZeeMaterialColors = darkColorScheme(
    primary = ZeeDarkColors.accent,
    onPrimary = ZeeDarkColors.onAccent,
    background = ZeeDarkColors.background,
    onBackground = ZeeDarkColors.content,
    surface = ZeeDarkColors.background,
    onSurface = ZeeDarkColors.content,
    onSurfaceVariant = ZeeDarkColors.contentSecondary,
    outline = ZeeDarkColors.content,
)

object ZeeTheme {
    val colors: ZeeColors
        @Composable
        @ReadOnlyComposable
        get() = LocalZeeColors.current

    val typography: ZeeTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalZeeTypography.current
}

@Composable
fun ZeeLauncherTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalZeeColors provides ZeeDarkColors,
        LocalZeeTypography provides ZeeDefaultTypography,
    ) {
        MaterialTheme(colorScheme = ZeeMaterialColors, content = content)
    }
}
