package com.zeelauncher.core_navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.zeelauncher.core_designsystem.motion.ZeeMotion

fun zeeTabEnter(): EnterTransition = ZeeMotion.enter()

fun zeeTabExit(): ExitTransition = ZeeMotion.exit()

fun zeeSlideEnter(): EnterTransition = slideInHorizontally(ZeeMotion.spec()) { it } + ZeeMotion.enter()

fun zeeSlideExit(): ExitTransition = slideOutHorizontally(ZeeMotion.spec()) { -it } + ZeeMotion.exit()

fun zeeSlidePopEnter(): EnterTransition = slideInHorizontally(ZeeMotion.spec()) { -it } + ZeeMotion.enter()

fun zeeSlidePopExit(): ExitTransition = slideOutHorizontally(ZeeMotion.spec()) { it } + ZeeMotion.exit()

inline fun <reified T : Any> NavGraphBuilder.tabComposable(
    noinline content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    composable<T>(
        enterTransition = { zeeTabEnter() },
        exitTransition = { zeeTabExit() },
        popEnterTransition = { zeeTabEnter() },
        popExitTransition = { zeeTabExit() },
        content = content,
    )
}

inline fun <reified T : Any> NavGraphBuilder.slideComposable(
    noinline content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    composable<T>(
        enterTransition = { zeeSlideEnter() },
        exitTransition = { zeeSlideExit() },
        popEnterTransition = { zeeSlidePopEnter() },
        popExitTransition = { zeeSlidePopExit() },
        content = content,
    )
}
