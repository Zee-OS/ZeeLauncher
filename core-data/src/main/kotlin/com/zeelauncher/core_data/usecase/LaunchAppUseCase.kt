package com.zeelauncher.core_data.usecase

import com.zeelauncher.core_model.app.InstalledApp
import com.zeelauncher.core_model.launch.LaunchResult

interface LaunchAppUseCase {

    operator fun invoke(app: InstalledApp): LaunchResult
}
