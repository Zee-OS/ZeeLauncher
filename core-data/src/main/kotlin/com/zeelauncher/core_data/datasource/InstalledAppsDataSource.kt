package com.zeelauncher.core_data.datasource

import com.zeelauncher.core_model.app.InstalledApp
import kotlinx.coroutines.flow.Flow

internal interface InstalledAppsDataSource {

    suspend fun getInstalledApps(): List<InstalledApp>

    fun observeChanges(): Flow<Unit>
}
