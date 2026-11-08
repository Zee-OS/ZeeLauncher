package com.zeelauncher.core_data.datasource

import kotlinx.coroutines.flow.Flow

internal interface SettingsLocalDataSource {

    fun observeSoundEnabled(): Flow<Boolean>

    suspend fun setSoundEnabled(enabled: Boolean)
}
