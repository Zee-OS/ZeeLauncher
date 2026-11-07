package com.zeelauncher.core_data.usecase

import com.zeelauncher.core_model.launch.LaunchResult

interface OpenAppInfoUseCase {

    operator fun invoke(packageName: String, activityName: String): LaunchResult
}
