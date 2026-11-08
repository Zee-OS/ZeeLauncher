package com.zeelauncher.core_data.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun observeSoundEnabled(): Flow<Boolean>

    suspend fun setSoundEnabled(enabled: Boolean)
}
