package com.zeelauncher.core_navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val TAB_TRANSITION_MILLIS = 180
const val SLIDE_TRANSITION_MILLIS = 260

fun zeeTabEnter(): EnterTransition = fadeIn(tween(TAB_TRANSITION_MILLIS))

fun zeeTabExit(): ExitTransition = fadeOut(tween(TAB_TRANSITION_MILLIS))

fun zeeSlideEnter(): EnterTransition =
    slideInHorizontally(tween(SLIDE_TRANSITION_MILLIS)) { it } + fadeIn(tween(SLIDE_TRANSITION_MILLIS))

fun zeeSlideExit(): ExitTransition =
    slideOutHorizontally(tween(SLIDE_TRANSITION_MILLIS)) { -it } + fadeOut(tween(SLIDE_TRANSITION_MILLIS))

fun zeeSlidePopEnter(): EnterTransition =
    slideInHorizontally(tween(SLIDE_TRANSITION_MILLIS)) { -it } + fadeIn(tween(SLIDE_TRANSITION_MILLIS))

fun zeeSlidePopExit(): ExitTransition =
    slideOutHorizontally(tween(SLIDE_TRANSITION_MILLIS)) { it } + fadeOut(tween(SLIDE_TRANSITION_MILLIS))

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
