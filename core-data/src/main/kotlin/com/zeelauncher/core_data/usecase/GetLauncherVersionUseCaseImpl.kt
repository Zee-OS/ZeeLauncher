package com.zeelauncher.core_data.usecase

import android.content.Context
import com.zeelauncher.core_data.repository.InstalledAppsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal class GetLauncherVersionUseCaseImpl @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val installedAppsRepository: InstalledAppsRepository,
) : GetLauncherVersionUseCase {

    override suspend fun invoke(): String? = installedAppsRepository.getVersionName(context.packageName)
}
