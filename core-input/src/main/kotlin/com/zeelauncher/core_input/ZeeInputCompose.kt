package com.zeelauncher.core_input

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.staticCompositionLocalOf

val LocalZeeInput = staticCompositionLocalOf { ZeeInputDispatcher() }

@Composable
fun ObserveZeeActions(onAction: (ZeeAction) -> Boolean) {
    val input = LocalZeeInput.current
    val currentOnAction by rememberUpdatedState(onAction)
    DisposableEffect(input) {
        val handler = ZeeActionHandler { currentOnAction(it) }
        input.register(handler)
        onDispose { input.unregister(handler) }
    }
}
