package com.zeelauncher.core_datastore

import kotlinx.coroutines.flow.Flow

interface ZeePreferences {

    val soundEnabled: Flow<Boolean>

    suspend fun setSoundEnabled(enabled: Boolean)
}
