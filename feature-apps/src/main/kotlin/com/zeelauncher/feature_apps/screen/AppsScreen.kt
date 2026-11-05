package com.zeelauncher.feature_apps.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.zeelauncher.core_designsystem.component.ZeePlaceholderScreen
import com.zeelauncher.core_designsystem.component.zeeBackHint
import com.zeelauncher.feature_apps.R

@Composable
internal fun AppsRoute() {
    AppsScreen()
}

@Composable
internal fun AppsScreen() {
    ZeePlaceholderScreen(
        message = stringResource(R.string.apps_coming_soon),
        hints = listOf(zeeBackHint()),
    )
}
