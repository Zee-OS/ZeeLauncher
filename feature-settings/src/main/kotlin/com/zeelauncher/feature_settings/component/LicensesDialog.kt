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
import com.zeelauncher.core_designsystem.theme.Dimen
import com.zeelauncher.core_designsystem.theme.ZeeTheme
import com.zeelauncher.feature_settings.R

private val DialogWidth = Dimen.Dp510
private val EntrySpacing = Dimen.Dp14
private val SectionSpacing = Dimen.Dp30

private val LicenseEntries = listOf(
    R.string.settings_license_androidx to R.string.settings_license_apache,
    R.string.settings_license_kotlin to R.string.settings_license_apache,
    R.string.settings_license_hilt to R.string.settings_license_apache,
    R.string.settings_license_coil to R.string.settings_license_apache,
    R.string.settings_license_rajdhani to R.string.settings_license_ofl,
)

@Composable
internal fun LicensesDialogContent() {
    val colors = ZeeTheme.colors
    Text(
        text = stringResource(R.string.settings_licenses_dialog_title),
        style = ZeeTheme.typography.headline,
        color = colors.title,
        maxLines = ONE,
    )
    Spacer(Modifier.height(SectionSpacing))
    Column(
        modifier = Modifier.width(DialogWidth),
        verticalArrangement = Arrangement.spacedBy(EntrySpacing),
    ) {
        LicenseEntries.forEach { (name, license) ->
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
    Spacer(Modifier.height(SectionSpacing))
    ZeeHintRow(listOf(zeeBackHint()))
}
