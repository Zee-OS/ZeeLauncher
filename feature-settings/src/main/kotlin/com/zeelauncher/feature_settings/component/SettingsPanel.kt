package com.zeelauncher.feature_settings.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.style.TextOverflow
import com.zeelauncher.core_common.constant.DASH
import com.zeelauncher.core_common.constant.ONE
import com.zeelauncher.core_common.constant.ONE_FLOAT
import com.zeelauncher.core_common.locale.AppLanguage
import com.zeelauncher.core_designsystem.component.ZeeButton
import com.zeelauncher.core_designsystem.component.ZeeButtonVariant
import com.zeelauncher.core_designsystem.modifier.hardShadow
import com.zeelauncher.core_designsystem.modifier.noRippleClickable
import com.zeelauncher.core_designsystem.theme.Dimen
import com.zeelauncher.core_designsystem.theme.TextDimen
import com.zeelauncher.core_designsystem.theme.ZeeDimens
import com.zeelauncher.core_designsystem.theme.ZeeTheme
import com.zeelauncher.feature_settings.R
import com.zeelauncher.feature_settings.viewmodel.SettingsRow
import com.zeelauncher.feature_settings.viewmodel.SettingsUiState

private val PanelPaddingHorizontal = Dimen.Dp40
private val PanelPaddingVertical = Dimen.Dp34
private val TitleSpacing = Dimen.Dp16
private val RowSpacing = Dimen.Dp8
private val RowPaddingHorizontal = Dimen.Dp20
private val RowPaddingVertical = Dimen.Dp14
private val ControlSpacing = Dimen.Dp24
private val SubtitleSpacing = Dimen.Dp4

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
            .padding(horizontal = PanelPaddingHorizontal, vertical = PanelPaddingVertical),
    ) {
        Text(
            text = stringResource(state.section.labelRes()),
            style = ZeeTheme.typography.headline,
            color = colors.title,
            maxLines = ONE,
        )
        Spacer(Modifier.height(TitleSpacing))
        Box(
            Modifier
                .fillMaxWidth()
                .height(ZeeDimens.BorderThick)
                .background(colors.content),
        )
        Spacer(Modifier.height(TitleSpacing))
        Column(verticalArrangement = Arrangement.spacedBy(RowSpacing)) {
            state.section.rows.forEachIndexed { index, row ->
                SettingsRowItem(
                    row = row,
                    state = state,
                    isFocused = state.isPanelFocused && index == state.rowIndex,
                    onClick = { onRowClick(row) },
                    onLanguageClick = onLanguageClick,
                )
            }
        }
    }
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
            .border(ZeeDimens.BorderThin, if (isFocused) colors.accent else Color.Transparent)
            .noRippleClickable(onClick)
            .padding(horizontal = RowPaddingHorizontal, vertical = RowPaddingVertical),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(Modifier.weight(ONE_FLOAT)) {
            Text(
                text = stringResource(row.titleRes()),
                style = ZeeTheme.typography.status.copy(fontSize = TextDimen.Sp22),
                color = if (isFocused) colors.accent else colors.title,
                maxLines = ONE,
            )
            Spacer(Modifier.height(SubtitleSpacing))
            Text(
                text = stringResource(row.subtitleRes()),
                style = ZeeTheme.typography.body,
                color = colors.contentSecondary,
                maxLines = ONE,
                overflow = TextOverflow.Ellipsis,
            )
        }
        Spacer(Modifier.width(ControlSpacing))
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
            Spacer(Modifier.width(ControlSpacing))
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
        SettingsRow.Rescan -> ZeeButton(
            label = stringResource(if (state.isRescanning) R.string.settings_rescan_running else R.string.settings_rescan_action),
            onClick = onClick,
            variant = ZeeButtonVariant.Secondary,
        )
        SettingsRow.Version -> Text(
            text = state.versionName?.uppercase() ?: DASH,
            style = ZeeTheme.typography.button,
            color = ZeeTheme.colors.title,
            maxLines = ONE,
        )
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
    SettingsRow.Rescan -> R.string.settings_rescan_title
    SettingsRow.Version -> R.string.settings_version_title
    SettingsRow.Licenses -> R.string.settings_licenses_title
}

private fun SettingsRow.subtitleRes(): Int = when (this) {
    SettingsRow.DefaultLauncher -> R.string.settings_default_launcher_subtitle
    SettingsRow.Language -> R.string.settings_language_subtitle
    SettingsRow.Sound -> R.string.settings_sound_subtitle
    SettingsRow.Rescan -> R.string.settings_rescan_subtitle
    SettingsRow.Version -> R.string.settings_version_subtitle
    SettingsRow.Licenses -> R.string.settings_licenses_subtitle
}

private fun AppLanguage.labelRes(): Int = when (this) {
    AppLanguage.English -> R.string.settings_language_en
    AppLanguage.Indonesian -> R.string.settings_language_id
}
