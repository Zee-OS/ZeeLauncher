package com.zeelauncher.core_designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.unit.lerp
import com.zeelauncher.core_designsystem.modifier.dashedBorder
import com.zeelauncher.core_designsystem.modifier.hardShadow
import com.zeelauncher.core_designsystem.theme.Dimen
import com.zeelauncher.core_designsystem.theme.ZeeDimens
import com.zeelauncher.core_designsystem.theme.ZeeTheme

@Composable
fun ZeeFocusFrame(
    focus: Float,
    modifier: Modifier = Modifier,
    dashed: Boolean = false,
    content: @Composable BoxScope.() -> Unit,
) {
    val colors = ZeeTheme.colors
    val lift = lerp(Dimen.Dp0, ZeeDimens.FocusLift, focus)
    val shadowOffset = lerp(ZeeDimens.ShadowCard, ZeeDimens.ShadowFocused, focus)
    val frameModifier = modifier
        .offset(lift, lift)
        .hardShadow(shadowOffset, lerp(colors.shadow, colors.accent, focus))
        .background(colors.background)
    Box(
        modifier = if (dashed) {
            frameModifier.dashedBorder(ZeeDimens.BorderThick, colors.content)
        } else {
            frameModifier.border(ZeeDimens.BorderThick, colors.content)
        },
        contentAlignment = Alignment.Center,
        content = content,
    )
}
