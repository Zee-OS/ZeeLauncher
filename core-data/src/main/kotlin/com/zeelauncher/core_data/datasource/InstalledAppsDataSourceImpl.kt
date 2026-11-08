package com.zeelauncher.core_data.datasource

import com.zeelauncher.core_model.app.InstalledApp
import com.zeelauncher.core_scanner.app.InstalledAppsSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class InstalledAppsDataSourceImpl @Inject constructor(
    private val installedAppsSource: InstalledAppsSource,
) : InstalledAppsDataSource {

    override suspend fun getInstalledApps(): List<InstalledApp> = installedAppsSource.getInstalledApps()

    override fun observeChanges(): Flow<Unit> = installedAppsSource.observeChanges()

    override suspend fun getVersionName(packageName: String): String? =
        installedAppsSource.getVersionName(packageName)
}
