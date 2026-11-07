package com.zeelauncher.core_designsystem.overlay

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterExitState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.Stable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import com.zeelauncher.core_designsystem.motion.ZeeMotion

internal class ZeeOverlayEntry(
    val visible: State<Boolean>,
    val content: State<@Composable AnimatedVisibilityScope.() -> Unit>,
)

@Stable
class ZeeOverlayHostState {

    internal val entries = mutableStateListOf<ZeeOverlayEntry>()
}

val LocalZeeOverlayHost = staticCompositionLocalOf { ZeeOverlayHostState() }

val AnimatedVisibilityScope.isOverlayActive: Boolean
    get() = transition.targetState == EnterExitState.Visible

@Composable
fun ZeeOverlay(
    visible: Boolean,
    content: @Composable AnimatedVisibilityScope.() -> Unit,
) {
    val host = LocalZeeOverlayHost.current
    val currentVisible = rememberUpdatedState(visible)
    val currentContent = rememberUpdatedState(content)
    val entry = remember { ZeeOverlayEntry(currentVisible, currentContent) }
    DisposableEffect(host, entry) {
        host.entries.add(entry)
        onDispose { host.entries.remove(entry) }
    }
}

@Composable
fun ZeeOverlayHost(
    state: ZeeOverlayHostState,
    modifier: Modifier = Modifier,
) {
    Box(modifier.fillMaxSize()) {
        state.entries.forEach { entry ->
            key(entry) {
                AnimatedVisibility(
                    visible = entry.visible.value,
                    enter = ZeeMotion.enter(),
                    exit = ZeeMotion.exit(),
                ) {
                    entry.content.value(this)
                }
            }
        }
    }
}
