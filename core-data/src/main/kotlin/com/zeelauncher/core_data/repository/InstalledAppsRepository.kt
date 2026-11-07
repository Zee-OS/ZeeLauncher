package com.zeelauncher.core_data.repository

import com.zeelauncher.core_model.app.InstalledApp
import kotlinx.coroutines.flow.Flow

interface InstalledAppsRepository {

    suspend fun getInstalledApps(): List<InstalledApp>

    fun observeChanges(): Flow<Unit>
}
