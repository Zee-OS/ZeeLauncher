package com.zeelauncher.core_datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val SOUND_ENABLED_KEY = "sound_enabled"
private const val DEFAULT_SOUND_ENABLED = true

internal class DataStoreZeePreferences @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : ZeePreferences {

    private val soundEnabledKey = booleanPreferencesKey(SOUND_ENABLED_KEY)

    override val soundEnabled: Flow<Boolean> = dataStore.data
        .map { it[soundEnabledKey] ?: DEFAULT_SOUND_ENABLED }
        .distinctUntilChanged()

    override suspend fun setSoundEnabled(enabled: Boolean) {
        dataStore.edit { it[soundEnabledKey] = enabled }
    }
}
