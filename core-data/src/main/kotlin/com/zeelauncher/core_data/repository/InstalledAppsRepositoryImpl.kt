package com.zeelauncher.core_data.repository

import com.zeelauncher.core_data.datasource.InstalledAppsDataSource
import com.zeelauncher.core_model.app.InstalledApp
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class InstalledAppsRepositoryImpl @Inject constructor(
    private val installedAppsDataSource: InstalledAppsDataSource,
) : InstalledAppsRepository {

    override suspend fun getInstalledApps(): List<InstalledApp> =
        installedAppsDataSource.getInstalledApps().sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it.label })

    override fun observeChanges(): Flow<Unit> = installedAppsDataSource.observeChanges()
}
