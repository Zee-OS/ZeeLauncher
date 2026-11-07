package com.zeelauncher.core_data.usecase

import com.zeelauncher.core_launch.AppLauncher
import com.zeelauncher.core_model.launch.LaunchResult
import javax.inject.Inject

internal class OpenAppInfoUseCaseImpl @Inject constructor(
    private val appLauncher: AppLauncher,
) : OpenAppInfoUseCase {

    override fun invoke(packageName: String, activityName: String): LaunchResult =
        appLauncher.openAppInfo(packageName, activityName)
}
