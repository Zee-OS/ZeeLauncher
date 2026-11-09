package com.zeelauncher.core_designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import com.zeelauncher.core_designsystem.attr.DesignSystemAttribute

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
        MaterialTheme(colorScheme = DesignSystemAttribute.Theme.MaterialColors, content = content)
    }
}
