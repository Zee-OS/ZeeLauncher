package com.zeelauncher.feature_settings.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.zeelauncher.core_common.constant.ONE
import com.zeelauncher.core_common.constant.TWO
import com.zeelauncher.core_common.constant.ZERO
import com.zeelauncher.core_designsystem.modifier.hardShadow
import com.zeelauncher.core_designsystem.modifier.noRippleClickable
import com.zeelauncher.core_designsystem.motion.ZeeMotion
import com.zeelauncher.core_designsystem.theme.Dimen
import com.zeelauncher.core_designsystem.theme.ZeeDimens
import com.zeelauncher.core_designsystem.theme.ZeeTheme
import com.zeelauncher.feature_settings.R
import com.zeelauncher.feature_settings.attr.SettingsFeatureAttribute

@Composable
internal fun SettingsStatus(enabled: Boolean) {
    Text(
        text = stringResource(if (enabled) R.string.settings_status_on else R.string.settings_status_off),
        style = ZeeTheme.typography.status,
        color = if (enabled) ZeeTheme.colors.title else ZeeTheme.colors.hint,
        maxLines = ONE,
    )
}

@Composable
internal fun SettingsToggle(
    checked: Boolean,
    onClick: () -> Unit,
) {
    val colors = ZeeTheme.colors
    val knobOffset by animateDpAsState(
        targetValue = if (checked) {
            SettingsFeatureAttribute.Controls.ToggleWidth -
                SettingsFeatureAttribute.Controls.ToggleKnobSize -
                SettingsFeatureAttribute.Controls.ToggleKnobInset * TWO
        } else {
            Dimen.Dp0
        },
        animationSpec = ZeeMotion.spec(),
        label = SettingsFeatureAttribute.Controls.KNOB_LABEL,
    )
    Row(verticalAlignment = Alignment.CenterVertically) {
        SettingsStatus(enabled = checked)
        Spacer(Modifier.width(SettingsFeatureAttribute.Controls.StatusSpacing))
        Box(
            modifier = Modifier
                .size(SettingsFeatureAttribute.Controls.ToggleWidth, SettingsFeatureAttribute.Controls.ToggleHeight)
                .hardShadow(ZeeDimens.ShadowSmall, colors.shadow)
                .background(if (checked) colors.accent else colors.background)
                .border(ZeeDimens.BorderThin, colors.content)
                .noRippleClickable(onClick)
                .padding(SettingsFeatureAttribute.Controls.ToggleKnobInset),
            contentAlignment = Alignment.CenterStart,
        ) {
            Box(
                Modifier
                    .offset(x = knobOffset)
                    .size(SettingsFeatureAttribute.Controls.ToggleKnobSize)
                    .background(if (checked) colors.content else colors.hint)
                    .border(ZeeDimens.BorderThin, colors.background),
            )
        }
    }
}

@Composable
internal fun <T> SettingsSegmented(
    options: List<T>,
    selected: T,
    label: @Composable (T) -> String,
    onSelect: (T) -> Unit,
) {
    val colors = ZeeTheme.colors
    Row(
        Modifier
            .height(IntrinsicSize.Min)
            .hardShadow(ZeeDimens.ShadowSmall, colors.shadow)
            .background(colors.background)
            .border(ZeeDimens.BorderThin, colors.content)
            .padding(ZeeDimens.BorderThin),
    ) {
        options.forEachIndexed { index, option ->
            if (index > ZERO) {
                Box(
                    Modifier
                        .width(ZeeDimens.BorderThin)
                        .fillMaxHeight()
                        .background(colors.content),
                )
            }
            val isSelected = option == selected
            Box(
                modifier = Modifier
                    .background(if (isSelected) colors.accent else colors.background)
                    .noRippleClickable { onSelect(option) }
                    .padding(horizontal = Dimen.Dp24, vertical = Dimen.Dp7),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = label(option),
                    style = ZeeTheme.typography.button,
                    color = if (isSelected) colors.onAccent else colors.content,
                    maxLines = ONE,
                )
            }
        }
    }
}
