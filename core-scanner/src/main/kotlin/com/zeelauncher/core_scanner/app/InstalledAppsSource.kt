package com.zeelauncher.core_scanner.app

import com.zeelauncher.core_model.app.InstalledApp
import kotlinx.coroutines.flow.Flow

interface InstalledAppsSource {

    suspend fun getInstalledApps(): List<InstalledApp>

    fun observeChanges(): Flow<Unit>

    suspend fun getVersionName(packageName: String): String?
}
