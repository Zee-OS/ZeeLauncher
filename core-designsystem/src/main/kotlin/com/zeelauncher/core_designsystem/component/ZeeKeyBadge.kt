package com.zeelauncher.core_designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.zeelauncher.core_common.constant.ONE
import com.zeelauncher.core_designsystem.modifier.hardShadow
import com.zeelauncher.core_designsystem.theme.Dimen
import com.zeelauncher.core_designsystem.theme.ZeeDimens
import com.zeelauncher.core_designsystem.theme.ZeeTheme

@Composable
fun ZeeKeyBadge(
    label: String,
    modifier: Modifier = Modifier,
    minWidth: Dp = ZeeDimens.BadgeSize,
) {
    val colors = ZeeTheme.colors
    Box(
        modifier
            .hardShadow(ZeeDimens.ShadowSmall, colors.shadow)
            .background(colors.content)
            .border(ZeeDimens.BorderThin, colors.content)
            .defaultMinSize(minWidth = minWidth)
            .height(ZeeDimens.BadgeSize)
            .padding(horizontal = Dimen.Dp8),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = label,
            style = ZeeTheme.typography.badge,
            color = colors.onAccent,
            maxLines = ONE,
        )
    }
}
