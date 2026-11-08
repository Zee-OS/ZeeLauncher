package com.zeelauncher.core_data.usecase

import com.zeelauncher.core_launch.LauncherRole
import javax.inject.Inject

internal class IsDefaultLauncherUseCaseImpl @Inject constructor(
    private val launcherRole: LauncherRole,
) : IsDefaultLauncherUseCase {

    override fun invoke(): Boolean = launcherRole.isDefaultLauncher()
}
