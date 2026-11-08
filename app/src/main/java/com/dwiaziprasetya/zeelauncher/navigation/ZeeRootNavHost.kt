package com.dwiaziprasetya.zeelauncher.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.dwiaziprasetya.zeelauncher.shell.MainShell
import com.zeelauncher.core_input.ObserveZeeActions
import com.zeelauncher.core_input.ZeeAction
import com.zeelauncher.core_navigation.slideComposable
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable

@Serializable
internal data object MainDestination

@Composable
internal fun ZeeRootNavHost(homeRequests: Flow<Unit>) {
    val navController = rememberNavController()

    LaunchedEffect(navController, homeRequests) {
        homeRequests.collect { navController.popBackStack(MainDestination, inclusive = false) }
    }

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
            MainShell(homeRequests = homeRequests)
        }
    }
}
