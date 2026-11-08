package com.zeelauncher.core_designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.zeelauncher.core_common.constant.ONE
import com.zeelauncher.core_designsystem.R
import com.zeelauncher.core_designsystem.modifier.hardShadow
import com.zeelauncher.core_designsystem.modifier.noRippleClickable
import com.zeelauncher.core_designsystem.sound.ZeeSoundEffect
import com.zeelauncher.core_designsystem.theme.Dimen
import com.zeelauncher.core_designsystem.theme.ZeeDimens
import com.zeelauncher.core_designsystem.theme.ZeeTheme

@Composable
fun ZeeTab(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = ZeeTheme.colors
    Box(
        modifier
            .hardShadow(ZeeDimens.ShadowMedium, colors.shadow)
            .background(if (selected) colors.accent else colors.background)
            .border(ZeeDimens.BorderThin, colors.content)
            .noRippleClickable(ZeeSoundEffect.Tab, onClick)
            .padding(horizontal = Dimen.Dp17, vertical = Dimen.Dp10),
    ) {
        Text(
            text = label,
            style = ZeeTheme.typography.button,
            color = if (selected) colors.onAccent else colors.content,
            maxLines = ONE,
        )
    }
}

@Composable
fun ZeeTabRow(
    labels: List<String>,
    selectedIndex: Int,
    onTabClick: (Int) -> Unit,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(Dimen.Dp16),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ZeeKeyBadge(
            label = stringResource(R.string.zee_key_l1),
            modifier = Modifier.noRippleClickable(ZeeSoundEffect.Tab, onPrevious),
            minWidth = ZeeDimens.BadgeWideMinWidth,
        )
        labels.forEachIndexed { index, label ->
            ZeeTab(label, index == selectedIndex, onClick = { onTabClick(index) })
        }
        ZeeKeyBadge(
            label = stringResource(R.string.zee_key_r1),
            modifier = Modifier.noRippleClickable(ZeeSoundEffect.Tab, onNext),
            minWidth = ZeeDimens.BadgeWideMinWidth,
        )
    }
}
