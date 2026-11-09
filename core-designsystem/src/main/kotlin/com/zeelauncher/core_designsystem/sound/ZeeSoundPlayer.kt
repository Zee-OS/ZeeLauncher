package com.zeelauncher.core_designsystem.sound

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import com.zeelauncher.core_common.constant.ONE
import com.zeelauncher.core_common.constant.ONE_FLOAT
import com.zeelauncher.core_common.constant.ZERO
import com.zeelauncher.core_designsystem.attr.DesignSystemAttribute

class ZeeSoundPlayer(context: Context) {

    private val soundPool = SoundPool.Builder()
        .setMaxStreams(DesignSystemAttribute.SoundPlayer.MAX_STREAMS)
        .setAudioAttributes(
            AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build(),
        )
        .build()

    private val loadedSoundIds = mutableSetOf<Int>()

    private val soundIds: Map<ZeeSoundEffect, Int>

    init {
        soundPool.setOnLoadCompleteListener { _, soundId, status ->
            if (status == ZERO) loadedSoundIds.add(soundId)
        }
        soundIds = ZeeSoundEffect.entries.associateWith { soundPool.load(context, it.resId, ONE) }
    }

    fun play(effect: ZeeSoundEffect) {
        val soundId = soundIds[effect] ?: return
        if (soundId !in loadedSoundIds) return
        soundPool.play(soundId, ONE_FLOAT, ONE_FLOAT, ONE, ZERO, ONE_FLOAT)
    }

    fun release() {
        soundPool.release()
    }
}
