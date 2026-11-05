package com.zeelauncher.feature_library.navigation

import androidx.navigation.NavGraphBuilder
import com.zeelauncher.core_navigation.tabComposable
import com.zeelauncher.feature_library.screen.LibraryRoute
import kotlinx.serialization.Serializable

@Serializable
data object LibraryDestination

fun NavGraphBuilder.libraryScreen() {
    tabComposable<LibraryDestination> {
        LibraryRoute()
    }
}
