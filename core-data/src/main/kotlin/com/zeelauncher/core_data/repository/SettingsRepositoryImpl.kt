package com.zeelauncher.core_data.repository

import com.zeelauncher.core_data.datasource.SettingsLocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class SettingsRepositoryImpl @Inject constructor(
    private val settingsLocalDataSource: SettingsLocalDataSource,
) : SettingsRepository {

    override fun observeSoundEnabled(): Flow<Boolean> = settingsLocalDataSource.observeSoundEnabled()

    override suspend fun setSoundEnabled(enabled: Boolean) {
        settingsLocalDataSource.setSoundEnabled(enabled)
    }
}
