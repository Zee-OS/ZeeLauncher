package com.zeelauncher.feature_settings.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.zeelauncher.core_common.constant.ONE
import com.zeelauncher.core_common.constant.ONE_FLOAT
import com.zeelauncher.core_common.constant.ZERO
import com.zeelauncher.core_common.locale.AppLanguage
import com.zeelauncher.core_designsystem.component.ZeeButton
import com.zeelauncher.core_designsystem.component.ZeeButtonVariant
import com.zeelauncher.core_designsystem.modifier.hardShadow
import com.zeelauncher.core_designsystem.modifier.noRippleClickable
import com.zeelauncher.core_designsystem.theme.TextDimen
import com.zeelauncher.core_designsystem.theme.ZeeDimens
import com.zeelauncher.core_designsystem.theme.ZeeTheme
import com.zeelauncher.feature_settings.R
import com.zeelauncher.feature_settings.attr.SettingsFeatureAttribute
import com.zeelauncher.feature_settings.viewmodel.SettingsRow
import com.zeelauncher.feature_settings.viewmodel.SettingsUiState

@Composable
internal fun SettingsPanel(
    state: SettingsUiState,
    onRowClick: (SettingsRow) -> Unit,
    onLanguageClick: (AppLanguage) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = ZeeTheme.colors
    Column(
        modifier = modifier
            .hardShadow(ZeeDimens.ShadowCard, colors.shadow)
            .background(colors.background)
            .border(ZeeDimens.BorderThick, colors.content)
            .padding(SettingsFeatureAttribute.Panel.Padding),
    ) {
        SettingsRow.entries.forEachIndexed { index, row ->
            if (index > ZERO) {
                RowDivider(visible = index != state.rowIndex && index - ONE != state.rowIndex)
            }
            SettingsRowItem(
                row = row,
                state = state,
                isFocused = index == state.rowIndex,
                onClick = { onRowClick(row) },
                onLanguageClick = onLanguageClick,
            )
        }
    }
}

@Composable
private fun RowDivider(visible: Boolean) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = SettingsFeatureAttribute.Panel.RowPaddingHorizontal)
            .height(SettingsFeatureAttribute.Panel.DividerThickness)
            .background(if (visible) ZeeTheme.colors.shadow else Color.Transparent),
    )
}

@Composable
private fun SettingsRowItem(
    row: SettingsRow,
    state: SettingsUiState,
    isFocused: Boolean,
    onClick: () -> Unit,
    onLanguageClick: (AppLanguage) -> Unit,
) {
    val colors = ZeeTheme.colors
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(SettingsFeatureAttribute.Panel.RowHeight)
            .hardShadow(ZeeDimens.ShadowMedium, if (isFocused) colors.accent else Color.Transparent)
            .background(colors.background)
            .border(ZeeDimens.BorderThin, if (isFocused) colors.content else Color.Transparent)
            .noRippleClickable(onClick)
            .padding(horizontal = SettingsFeatureAttribute.Panel.RowPaddingHorizontal),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(row.titleRes()),
            style = ZeeTheme.typography.status.copy(fontSize = TextDimen.Sp22),
            color = colors.title,
            maxLines = ONE,
            modifier = Modifier.weight(ONE_FLOAT),
        )
        Spacer(Modifier.width(SettingsFeatureAttribute.Panel.ControlSpacing))
        RowControl(row = row, state = state, onClick = onClick, onLanguageClick = onLanguageClick)
    }
}

@Composable
private fun RowControl(
    row: SettingsRow,
    state: SettingsUiState,
    onClick: () -> Unit,
    onLanguageClick: (AppLanguage) -> Unit,
) {
    when (row) {
        SettingsRow.DefaultLauncher -> Row(verticalAlignment = Alignment.CenterVertically) {
            SettingsStatus(enabled = state.isDefaultLauncher)
            Spacer(Modifier.width(SettingsFeatureAttribute.Panel.ControlSpacing))
            ZeeButton(
                label = stringResource(
                    if (state.isDefaultLauncher) R.string.settings_default_launcher_change else R.string.settings_default_launcher_set,
                ),
                onClick = onClick,
                variant = ZeeButtonVariant.Secondary,
            )
        }
        SettingsRow.Language -> SettingsSegmented(
            options = AppLanguage.entries,
            selected = state.language,
            label = { stringResource(it.labelRes()) },
            onSelect = onLanguageClick,
        )
        SettingsRow.Sound -> SettingsToggle(checked = state.soundEnabled, onClick = onClick)
        SettingsRow.Licenses -> ZeeButton(
            label = stringResource(R.string.settings_licenses_action),
            onClick = onClick,
            variant = ZeeButtonVariant.Secondary,
        )
    }
}

private fun SettingsRow.titleRes(): Int = when (this) {
    SettingsRow.DefaultLauncher -> R.string.settings_default_launcher_title
    SettingsRow.Language -> R.string.settings_language_title
    SettingsRow.Sound -> R.string.settings_sound_title
    SettingsRow.Licenses -> R.string.settings_licenses_title
}

private fun AppLanguage.labelRes(): Int = when (this) {
    AppLanguage.English -> R.string.settings_language_en
    AppLanguage.Indonesian -> R.string.settings_language_id
}
