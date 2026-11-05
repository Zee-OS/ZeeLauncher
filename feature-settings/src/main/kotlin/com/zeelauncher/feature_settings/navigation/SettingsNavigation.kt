package com.zeelauncher.feature_settings.navigation

import androidx.navigation.NavGraphBuilder
import com.zeelauncher.core_navigation.tabComposable
import com.zeelauncher.feature_settings.screen.SettingsRoute
import kotlinx.serialization.Serializable

@Serializable
data object SettingsDestination

fun NavGraphBuilder.settingsScreen() {
    tabComposable<SettingsDestination> {
        SettingsRoute()
    }
}
