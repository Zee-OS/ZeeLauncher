package com.zeelauncher.core_data.usecase

import com.zeelauncher.core_launch.AppLauncher
import com.zeelauncher.core_model.launch.LaunchResult
import javax.inject.Inject

internal class UninstallAppUseCaseImpl @Inject constructor(
    private val appLauncher: AppLauncher,
) : UninstallAppUseCase {

    override fun invoke(packageName: String): LaunchResult = appLauncher.uninstall(packageName)
}
