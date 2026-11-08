package com.zeelauncher.core_data.usecase

import com.zeelauncher.core_model.app.InstalledApp

interface AddGameToLibraryUseCase {

    suspend operator fun invoke(app: InstalledApp)
}
