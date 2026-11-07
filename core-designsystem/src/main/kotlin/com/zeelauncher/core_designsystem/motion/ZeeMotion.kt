package com.zeelauncher.core_designsystem.motion

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut

object ZeeMotion {

    const val DURATION_MILLIS = 220

    val easing: Easing = FastOutSlowInEasing

    fun <T> spec(): TweenSpec<T> = tween(durationMillis = DURATION_MILLIS, easing = easing)

    fun enter(): EnterTransition = fadeIn(spec())

    fun exit(): ExitTransition = fadeOut(spec())

    fun crossfade(): ContentTransform = ContentTransform(
        targetContentEnter = enter(),
        initialContentExit = exit(),
        sizeTransform = SizeTransform(clip = false) { _, _ -> spec() },
    )
}
