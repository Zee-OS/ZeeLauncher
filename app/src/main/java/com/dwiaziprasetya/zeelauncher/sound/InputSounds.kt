package com.dwiaziprasetya.zeelauncher.sound

import androidx.activity.ComponentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.zeelauncher.core_designsystem.sound.ZeeSoundEffect
import com.zeelauncher.core_designsystem.sound.ZeeSoundPlayback
import com.zeelauncher.core_designsystem.sound.ZeeSoundPlayer
import com.zeelauncher.core_input.ZeeAction
import com.zeelauncher.core_input.ZeeInputDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

internal fun ComponentActivity.attachInputSounds(
    input: ZeeInputDispatcher,
    soundEnabled: Flow<Boolean>,
): ZeeSoundPlayback {
    val player = ZeeSoundPlayer(this)
    var isEnabled = false
    val playback = ZeeSoundPlayback { effect -> if (isEnabled) player.play(effect) }

    lifecycleScope.launch {
        soundEnabled.collect { isEnabled = it }
    }

    input.onActionHandled = { action -> action.soundEffect()?.let(playback::play) }

    lifecycle.addObserver(
        object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                input.onActionHandled = null
                player.release()
            }
        },
    )
    return playback
}

private fun ZeeAction.soundEffect(): ZeeSoundEffect? = when (this) {
    ZeeAction.Left, ZeeAction.Right, ZeeAction.Up, ZeeAction.Down -> ZeeSoundEffect.Move
    ZeeAction.Confirm, ZeeAction.AppInfo, ZeeAction.Menu -> ZeeSoundEffect.Select
    ZeeAction.Back -> ZeeSoundEffect.Back
    ZeeAction.PreviousTab, ZeeAction.NextTab -> ZeeSoundEffect.Tab
    ZeeAction.Search -> null
}
