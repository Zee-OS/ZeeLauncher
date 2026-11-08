package com.zeelauncher.core_data.usecase

import com.zeelauncher.core_data.repository.LibraryRepository
import com.zeelauncher.core_model.app.InstalledApp
import javax.inject.Inject

internal class AddGameToLibraryUseCaseImpl @Inject constructor(
    private val libraryRepository: LibraryRepository,
) : AddGameToLibraryUseCase {

    override suspend fun invoke(app: InstalledApp) {
        libraryRepository.addGame(app)
    }
}
