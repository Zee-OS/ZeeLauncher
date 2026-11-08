package com.dwiaziprasetya.zeelauncher.shell

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dwiaziprasetya.zeelauncher.navigation.MainNavHost
import com.zeelauncher.core_common.constant.ONE
import com.zeelauncher.core_common.constant.ONE_FLOAT
import com.zeelauncher.core_common.constant.ZERO
import com.zeelauncher.core_designsystem.component.ZeeDrawer
import com.zeelauncher.core_designsystem.overlay.ZeeOverlay
import com.zeelauncher.core_designsystem.overlay.isOverlayActive
import com.zeelauncher.core_designsystem.theme.ZeeDimens
import com.zeelauncher.core_input.ObserveZeeActions
import com.zeelauncher.core_input.ZeeAction
import kotlinx.coroutines.flow.Flow

@Composable
internal fun MainShell(homeRequests: Flow<Unit>) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val tabs = MainTab.entries
    val selectedTab = tabs.firstOrNull { tab ->
        backStackEntry?.destination?.hierarchy?.any { it.hasRoute(tab.destination::class) } == true
    } ?: MainTab.Library

    val openTab: (MainTab) -> Unit = { navController.navigateToTab(it) }
    val openPrevious = { openTab(tabs[(selectedTab.ordinal - ONE).coerceAtLeast(ZERO)]) }
    val openNext = { openTab(tabs[(selectedTab.ordinal + ONE).coerceAtMost(tabs.lastIndex)]) }

    var isStatusOpen by remember { mutableStateOf(false) }
    val status = rememberDeviceStatus()

    LaunchedEffect(navController, homeRequests) {
        homeRequests.collect {
            isStatusOpen = false
            navController.navigateToTab(MainTab.Library)
        }
    }

    ObserveZeeActions { action ->
        when (action) {
            ZeeAction.PreviousTab -> {
                openPrevious()
                true
            }
            ZeeAction.NextTab -> {
                openNext()
                true
            }
            ZeeAction.Menu -> {
                isStatusOpen = true
                true
            }
            ZeeAction.Back -> if (selectedTab != MainTab.Library) {
                openTab(MainTab.Library)
                true
            } else {
                false
            }
            else -> false
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(vertical = ZeeDimens.ScreenPaddingVertical),
    ) {
        MainHeader(
            selectedTab = selectedTab,
            onTabClick = openTab,
            onPrevious = openPrevious,
            onNext = openNext,
            status = status,
            onStatusClick = { isStatusOpen = true },
            modifier = Modifier.padding(horizontal = ZeeDimens.ScreenPaddingHorizontal),
        )
        MainNavHost(
            navController = navController,
            modifier = Modifier
                .weight(ONE_FLOAT)
                .fillMaxWidth(),
        )
    }

    ZeeOverlay(visible = isStatusOpen) {
        val isActive = isOverlayActive
        ObserveZeeActions { action ->
            if (isActive && (action == ZeeAction.Back || action == ZeeAction.Menu)) isStatusOpen = false
            isActive
        }
        ZeeDrawer(onDismiss = { isStatusOpen = false }) {
            StatusDrawerContent(status = status)
        }
    }
}

private fun NavHostController.navigateToTab(tab: MainTab) {
    navigate(tab.destination) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}
