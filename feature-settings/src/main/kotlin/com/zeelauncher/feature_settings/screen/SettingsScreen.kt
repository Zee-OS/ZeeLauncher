package com.zeelauncher.feature_settings.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.zeelauncher.core_designsystem.component.ZeePlaceholderScreen
import com.zeelauncher.core_designsystem.component.zeeBackHint
import com.zeelauncher.feature_settings.R

@Composable
internal fun SettingsRoute() {
    SettingsScreen()
}

@Composable
internal fun SettingsScreen() {
    ZeePlaceholderScreen(
        message = stringResource(R.string.settings_coming_soon),
        hints = listOf(zeeBackHint()),
    )
}
