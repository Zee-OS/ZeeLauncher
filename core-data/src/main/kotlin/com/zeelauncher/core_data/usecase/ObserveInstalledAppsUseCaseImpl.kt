package com.zeelauncher.core_data.usecase

import com.zeelauncher.core_data.repository.InstalledAppsRepository
import com.zeelauncher.core_model.app.InstalledApp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class ObserveInstalledAppsUseCaseImpl @Inject constructor(
    private val installedAppsRepository: InstalledAppsRepository,
) : ObserveInstalledAppsUseCase {

    override fun invoke(): Flow<List<InstalledApp>> = flow {
        emit(installedAppsRepository.getInstalledApps())
        installedAppsRepository.observeChanges().collect {
            emit(installedAppsRepository.getInstalledApps())
        }
    }
}
