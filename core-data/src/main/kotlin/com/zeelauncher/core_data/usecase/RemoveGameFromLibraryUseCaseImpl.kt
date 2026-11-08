package com.zeelauncher.core_data.usecase

import com.zeelauncher.core_data.repository.LibraryRepository
import javax.inject.Inject

internal class RemoveGameFromLibraryUseCaseImpl @Inject constructor(
    private val libraryRepository: LibraryRepository,
) : RemoveGameFromLibraryUseCase {

    override suspend fun invoke(packageName: String) {
        libraryRepository.hideGame(packageName)
    }
}
