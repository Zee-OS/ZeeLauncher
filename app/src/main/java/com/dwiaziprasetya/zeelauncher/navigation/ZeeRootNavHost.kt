package com.dwiaziprasetya.zeelauncher.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.dwiaziprasetya.zeelauncher.shell.MainShell
import com.zeelauncher.core_input.ObserveZeeActions
import com.zeelauncher.core_input.ZeeAction
import com.zeelauncher.core_navigation.slideComposable
import kotlinx.serialization.Serializable

@Serializable
internal data object MainDestination

@Composable
internal fun ZeeRootNavHost() {
    val navController = rememberNavController()

    ObserveZeeActions { action ->
        if (action == ZeeAction.Back) {
            if (navController.previousBackStackEntry != null) navController.popBackStack()
            true
        } else {
            false
        }
    }

    NavHost(
        navController = navController,
        startDestination = MainDestination,
    ) {
        slideComposable<MainDestination> {
            MainShell()
        }
    }
}
