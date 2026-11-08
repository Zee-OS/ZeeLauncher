package com.zeelauncher.core_data.usecase

import com.zeelauncher.core_data.repository.InstalledAppsRepository
import javax.inject.Inject

internal class GetAppVersionUseCaseImpl @Inject constructor(
    private val installedAppsRepository: InstalledAppsRepository,
) : GetAppVersionUseCase {

    override suspend fun invoke(packageName: String): String? =
        installedAppsRepository.getVersionName(packageName)
}
