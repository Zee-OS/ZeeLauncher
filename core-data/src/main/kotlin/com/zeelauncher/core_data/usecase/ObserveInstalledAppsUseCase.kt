package com.zeelauncher.core_data.usecase

import com.zeelauncher.core_model.app.InstalledApp
import kotlinx.coroutines.flow.Flow

interface ObserveInstalledAppsUseCase {

    operator fun invoke(): Flow<List<InstalledApp>>
}
