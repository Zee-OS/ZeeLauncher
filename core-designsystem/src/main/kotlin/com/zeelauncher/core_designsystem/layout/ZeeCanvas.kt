package com.zeelauncher.core_designsystem.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import com.zeelauncher.core_common.constant.ONE_FLOAT
import com.zeelauncher.core_designsystem.theme.ZeeDimens
import com.zeelauncher.core_designsystem.theme.ZeeTheme
import kotlin.math.min

@Composable
fun ZeeCanvas(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier
            .fillMaxSize()
            .background(ZeeTheme.colors.background)
            .windowInsetsPadding(WindowInsets.displayCutout),
    ) {
        BoxWithConstraints(Modifier.fillMaxSize()) {
            val scale = min(
                maxWidth / ZeeDimens.DesignWidth,
                maxHeight / ZeeDimens.DesignHeight,
            )
            val density = LocalDensity.current
            CompositionLocalProvider(
                LocalDensity provides Density(density.density * scale, ONE_FLOAT),
                content = content,
            )
        }
    }
}
