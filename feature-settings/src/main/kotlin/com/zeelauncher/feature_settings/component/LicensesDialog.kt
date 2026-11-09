package com.zeelauncher.feature_settings.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.zeelauncher.core_common.constant.ONE
import com.zeelauncher.core_common.constant.ONE_FLOAT
import com.zeelauncher.core_designsystem.component.ZeeHintRow
import com.zeelauncher.core_designsystem.component.zeeBackHint
import com.zeelauncher.core_designsystem.theme.ZeeTheme
import com.zeelauncher.feature_settings.R
import com.zeelauncher.feature_settings.attr.SettingsFeatureAttribute

@Composable
internal fun LicensesDialogContent() {
    val colors = ZeeTheme.colors
    Text(
        text = stringResource(R.string.settings_licenses_dialog_title),
        style = ZeeTheme.typography.headline,
        color = colors.title,
        maxLines = ONE,
    )
    Spacer(Modifier.height(SettingsFeatureAttribute.LicensesDialog.SectionSpacing))
    Column(
        modifier = Modifier.width(SettingsFeatureAttribute.LicensesDialog.Width),
        verticalArrangement = Arrangement.spacedBy(SettingsFeatureAttribute.LicensesDialog.EntrySpacing),
    ) {
        SettingsFeatureAttribute.LicensesDialog.LicenseEntries.forEach { (name, license) ->
            Row {
                Text(
                    text = stringResource(name),
                    style = ZeeTheme.typography.button,
                    color = colors.title,
                    maxLines = ONE,
                    modifier = Modifier.weight(ONE_FLOAT),
                )
                Text(
                    text = stringResource(license),
                    style = ZeeTheme.typography.label,
                    color = colors.contentSecondary,
                    maxLines = ONE,
                )
            }
        }
    }
    Spacer(Modifier.height(SettingsFeatureAttribute.LicensesDialog.SectionSpacing))
    ZeeHintRow(listOf(zeeBackHint()))
}
