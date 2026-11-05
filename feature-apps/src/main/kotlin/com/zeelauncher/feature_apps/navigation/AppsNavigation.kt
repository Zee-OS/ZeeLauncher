package com.zeelauncher.feature_apps.navigation

import androidx.navigation.NavGraphBuilder
import com.zeelauncher.core_navigation.tabComposable
import com.zeelauncher.feature_apps.screen.AppsRoute
import kotlinx.serialization.Serializable

@Serializable
data object AppsDestination

fun NavGraphBuilder.appsScreen() {
    tabComposable<AppsDestination> {
        AppsRoute()
    }
}
