package com.zeelauncher.feature_settings.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.zeelauncher.core_common.constant.ONE
import com.zeelauncher.core_common.constant.ONE_FLOAT
import com.zeelauncher.core_designsystem.modifier.hardShadow
import com.zeelauncher.core_designsystem.modifier.noRippleClickable
import com.zeelauncher.core_designsystem.sound.ZeeSoundEffect
import com.zeelauncher.core_designsystem.theme.Dimen
import com.zeelauncher.core_designsystem.theme.ZeeDimens
import com.zeelauncher.core_designsystem.theme.ZeeTheme
import com.zeelauncher.feature_settings.R
import com.zeelauncher.feature_settings.attr.SettingsFeatureAttribute
import com.zeelauncher.feature_settings.viewmodel.SettingsSection

@Composable
internal fun SettingsSidebar(
    selected: SettingsSection,
    isFocused: Boolean,
    onSectionClick: (SettingsSection) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(SettingsFeatureAttribute.Sidebar.ItemSpacing),
    ) {
        SettingsSection.entries.forEach { section ->
            SidebarItem(
                label = stringResource(section.labelRes()),
                isActive = section == selected,
                showChevron = section == selected && isFocused,
                onClick = { onSectionClick(section) },
            )
        }
    }
}

@Composable
private fun SidebarItem(
    label: String,
    isActive: Boolean,
    showChevron: Boolean,
    onClick: () -> Unit,
) {
    val colors = ZeeTheme.colors
    val base = Modifier
        .fillMaxWidth()
        .height(SettingsFeatureAttribute.Sidebar.ItemHeight)
    val surface = if (isActive) {
        base.hardShadow(ZeeDimens.ShadowMedium, colors.shadow).background(colors.accent)
    } else {
        base.background(colors.background)
    }
    val textColor = if (isActive) colors.onAccent else colors.content
    Row(
        modifier = surface
            .border(ZeeDimens.BorderThin, colors.content)
            .noRippleClickable(ZeeSoundEffect.Move, onClick)
            .padding(horizontal = Dimen.Dp30),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            style = ZeeTheme.typography.button,
            color = textColor,
            maxLines = ONE,
            modifier = Modifier.weight(ONE_FLOAT),
        )
        if (showChevron) {
            Text(
                text = stringResource(R.string.settings_chevron),
                style = ZeeTheme.typography.button,
                color = textColor,
            )
        }
    }
}

internal fun SettingsSection.labelRes(): Int = when (this) {
    SettingsSection.General -> R.string.settings_section_general
    SettingsSection.Library -> R.string.settings_section_library
    SettingsSection.About -> R.string.settings_section_about
}
