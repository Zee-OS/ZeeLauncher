package com.zeelauncher.feature_settings.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.zeelauncher.core_common.constant.ONE
import com.zeelauncher.core_designsystem.component.ZeeButtonHint
import com.zeelauncher.core_designsystem.component.ZeeHintRow
import com.zeelauncher.core_designsystem.modifier.noRippleClickable
import com.zeelauncher.core_designsystem.theme.ZeeTheme
import com.zeelauncher.feature_settings.R
import com.zeelauncher.feature_settings.attr.SettingsFeatureAttribute
import com.zeelauncher.core_designsystem.R as DesignR

@Composable
internal fun LicensesDialogContent(onOpenClick: () -> Unit) {
    val colors = ZeeTheme.colors
    Column(Modifier.width(SettingsFeatureAttribute.LicensesDialog.Width)) {
        Text(
            text = stringResource(R.string.settings_licenses_dialog_title),
            style = ZeeTheme.typography.headline,
            color = colors.title,
            maxLines = ONE,
        )
        Spacer(Modifier.height(SettingsFeatureAttribute.LicensesDialog.TitleSpacing))
        Text(
            text = stringResource(R.string.settings_licenses_dialog_body),
            style = ZeeTheme.typography.body,
            color = colors.hint,
        )
        Spacer(Modifier.height(SettingsFeatureAttribute.LicensesDialog.LinkSpacing))
        Text(
            text = stringResource(R.string.settings_source_label),
            style = ZeeTheme.typography.button,
            color = colors.accent,
            maxLines = ONE,
            modifier = Modifier.noRippleClickable(onOpenClick),
        )
        Spacer(Modifier.height(SettingsFeatureAttribute.LicensesDialog.SectionSpacing))
        ZeeHintRow(
            listOf(
                ZeeButtonHint(stringResource(DesignR.string.zee_key_a), stringResource(R.string.settings_hint_open_github)),
                ZeeButtonHint(stringResource(DesignR.string.zee_key_b), stringResource(R.string.settings_hint_close)),
            ),
        )
    }
}
