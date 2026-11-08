package com.zeelauncher.core_data.usecase

import com.zeelauncher.core_model.app.InstalledApp

interface GetImportableAppsUseCase {

    suspend operator fun invoke(): List<InstalledApp>
}
