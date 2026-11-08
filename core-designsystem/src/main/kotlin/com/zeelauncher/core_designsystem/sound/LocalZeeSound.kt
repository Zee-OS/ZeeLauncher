package com.zeelauncher.core_designsystem.sound

import androidx.compose.runtime.staticCompositionLocalOf

fun interface ZeeSoundPlayback {
    fun play(effect: ZeeSoundEffect)
}

val LocalZeeSound = staticCompositionLocalOf { ZeeSoundPlayback {} }
