package com.zeelauncher.core_designsystem.motion

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut

object ZeeMotion {

    const val DURATION_MILLIS = 220

    const val EASING_X1 = 0.4f
    const val EASING_Y1 = 0f
    const val EASING_X2 = 0.2f
    const val EASING_Y2 = 1f

    val easing: Easing = CubicBezierEasing(EASING_X1, EASING_Y1, EASING_X2, EASING_Y2)

    fun <T> spec(): TweenSpec<T> = tween(durationMillis = DURATION_MILLIS, easing = easing)

    fun enter(): EnterTransition = fadeIn(spec())

    fun exit(): ExitTransition = fadeOut(spec())

    fun crossfade(): ContentTransform = ContentTransform(
        targetContentEnter = enter(),
        initialContentExit = exit(),
        sizeTransform = SizeTransform(clip = false) { _, _ -> spec() },
    )
}
