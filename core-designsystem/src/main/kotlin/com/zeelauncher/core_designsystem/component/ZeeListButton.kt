package com.zeelauncher.core_designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.zeelauncher.core_common.constant.ONE
import com.zeelauncher.core_designsystem.modifier.hardShadow
import com.zeelauncher.core_designsystem.modifier.noRippleClickable
import com.zeelauncher.core_designsystem.theme.Dimen
import com.zeelauncher.core_designsystem.theme.ZeeDimens
import com.zeelauncher.core_designsystem.theme.ZeeTheme

@Composable
fun ZeeListButton(
    label: String,
    focused: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = ZeeTheme.colors
    val base = modifier.fillMaxWidth()
    val surface = if (focused) {
        base.hardShadow(ZeeDimens.ShadowMedium, colors.shadow).background(colors.accent)
    } else {
        base.background(colors.background)
    }
    Box(
        surface
            .border(ZeeDimens.BorderThin, colors.content)
            .noRippleClickable(onClick)
            .padding(horizontal = Dimen.Dp20, vertical = Dimen.Dp11),
    ) {
        Text(
            text = label,
            style = ZeeTheme.typography.button,
            color = if (focused) colors.onAccent else colors.content,
            maxLines = ONE,
        )
    }
}
