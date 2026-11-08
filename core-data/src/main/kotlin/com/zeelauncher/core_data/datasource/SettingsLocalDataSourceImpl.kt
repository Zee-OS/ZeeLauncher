package com.zeelauncher.core_data.datasource

import com.zeelauncher.core_datastore.ZeePreferences
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class SettingsLocalDataSourceImpl @Inject constructor(
    private val zeePreferences: ZeePreferences,
) : SettingsLocalDataSource {

    override fun observeSoundEnabled(): Flow<Boolean> = zeePreferences.soundEnabled

    override suspend fun setSoundEnabled(enabled: Boolean) {
        zeePreferences.setSoundEnabled(enabled)
    }
}
