package com.zeelauncher.core_designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
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

enum class ZeeButtonVariant { Primary, Secondary }

@Composable
fun ZeeButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: ZeeButtonVariant = ZeeButtonVariant.Primary,
) {
    val colors = ZeeTheme.colors
    val primary = variant == ZeeButtonVariant.Primary
    Box(
        modifier
            .hardShadow(ZeeDimens.ShadowMedium, colors.shadow)
            .background(if (primary) colors.accent else colors.background)
            .border(ZeeDimens.BorderThin, colors.content)
            .noRippleClickable(onClick)
            .padding(
                horizontal = if (primary) Dimen.Dp31 else Dimen.Dp21,
                vertical = Dimen.Dp11,
            ),
    ) {
        Text(
            text = label,
            style = if (primary) ZeeTheme.typography.button else ZeeTheme.typography.buttonSmall,
            color = if (primary) colors.onAccent else colors.content,
            maxLines = ONE,
        )
    }
}
