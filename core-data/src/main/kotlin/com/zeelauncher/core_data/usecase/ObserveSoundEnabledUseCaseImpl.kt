package com.zeelauncher.core_data.usecase

import com.zeelauncher.core_data.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class ObserveSoundEnabledUseCaseImpl @Inject constructor(
    private val settingsRepository: SettingsRepository,
) : ObserveSoundEnabledUseCase {

    override fun invoke(): Flow<Boolean> = settingsRepository.observeSoundEnabled()
}
