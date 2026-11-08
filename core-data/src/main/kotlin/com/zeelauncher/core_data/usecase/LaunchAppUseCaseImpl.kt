package com.zeelauncher.core_data.usecase

import com.zeelauncher.core_launch.AppLauncher
import com.zeelauncher.core_model.app.InstalledApp
import com.zeelauncher.core_model.launch.LaunchResult
import javax.inject.Inject

internal class LaunchAppUseCaseImpl @Inject constructor(
    private val appLauncher: AppLauncher,
) : LaunchAppUseCase {

    override fun invoke(app: InstalledApp): LaunchResult = appLauncher.launch(app.packageName, app.activityName)
}
