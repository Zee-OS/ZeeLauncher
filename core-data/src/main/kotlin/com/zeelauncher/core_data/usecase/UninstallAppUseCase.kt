package com.zeelauncher.core_data.usecase

import com.zeelauncher.core_model.launch.LaunchResult

interface UninstallAppUseCase {

    operator fun invoke(packageName: String): LaunchResult
}
