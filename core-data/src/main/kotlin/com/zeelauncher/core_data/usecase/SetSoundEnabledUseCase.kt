package com.zeelauncher.core_data.usecase

interface SetSoundEnabledUseCase {

    suspend operator fun invoke(enabled: Boolean)
}
