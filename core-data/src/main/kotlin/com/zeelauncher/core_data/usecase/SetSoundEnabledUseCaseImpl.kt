package com.zeelauncher.core_data.usecase

import com.zeelauncher.core_data.repository.SettingsRepository
import javax.inject.Inject

internal class SetSoundEnabledUseCaseImpl @Inject constructor(
    private val settingsRepository: SettingsRepository,
) : SetSoundEnabledUseCase {

    override suspend fun invoke(enabled: Boolean) {
        settingsRepository.setSoundEnabled(enabled)
    }
}
