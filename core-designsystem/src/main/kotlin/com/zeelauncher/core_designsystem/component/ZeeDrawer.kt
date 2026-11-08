package com.zeelauncher.core_designsystem.component

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.core.animateFloat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.graphicsLayer
import com.zeelauncher.core_common.constant.ONE_FLOAT
import com.zeelauncher.core_common.constant.ZERO_FLOAT
import com.zeelauncher.core_designsystem.modifier.noRippleClickable
import com.zeelauncher.core_designsystem.motion.ZeeMotion
import com.zeelauncher.core_designsystem.theme.ZeeDimens
import com.zeelauncher.core_designsystem.theme.ZeeTheme

private const val DRAWER_SLIDE_LABEL = "drawer_slide"

@Composable
fun AnimatedVisibilityScope.ZeeDrawer(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    val colors = ZeeTheme.colors
    val slide by transition.animateFloat(
        transitionSpec = { ZeeMotion.spec() },
        label = DRAWER_SLIDE_LABEL,
    ) { state -> if (state == EnterExitState.Visible) ZERO_FLOAT else ONE_FLOAT }

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.CenterEnd) {
        ZeeScrim(onDismiss = onDismiss)
        Column(
            modifier = modifier
                .fillMaxHeight()
                .width(ZeeDimens.DrawerWidth)
                .graphicsLayer { translationX = size.width * slide }
                .background(colors.background)
                .drawBehind {
                    drawRect(
                        color = colors.content,
                        size = Size(ZeeDimens.BorderThick.toPx(), size.height),
                    )
                }
                .noRippleClickable(null) {}
                .padding(ZeeDimens.DrawerPadding),
            content = content,
        )
    }
}
