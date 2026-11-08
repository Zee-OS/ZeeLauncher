package com.zeelauncher.core_data.usecase

import com.zeelauncher.core_data.repository.InstalledAppsRepository
import com.zeelauncher.core_data.repository.LibraryRepository
import com.zeelauncher.core_model.app.InstalledApp
import kotlinx.coroutines.flow.first
import javax.inject.Inject

internal class GetImportableAppsUseCaseImpl @Inject constructor(
    private val installedAppsRepository: InstalledAppsRepository,
    private val libraryRepository: LibraryRepository,
) : GetImportableAppsUseCase {

    override suspend fun invoke(): List<InstalledApp> {
        val libraryPackageNames = libraryRepository.observeLibrary().first().map { it.packageName }.toSet()
        return installedAppsRepository.getInstalledApps().filter { it.packageName !in libraryPackageNames }
    }
}
