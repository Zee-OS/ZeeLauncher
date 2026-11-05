package com.dwiaziprasetya.zeelauncher.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.zeelauncher.feature_apps.navigation.appsScreen
import com.zeelauncher.feature_library.navigation.LibraryDestination
import com.zeelauncher.feature_library.navigation.libraryScreen
import com.zeelauncher.feature_settings.navigation.settingsScreen

@Composable
internal fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = LibraryDestination,
        modifier = modifier,
    ) {
        libraryScreen()
        appsScreen()
        settingsScreen()
    }
}
