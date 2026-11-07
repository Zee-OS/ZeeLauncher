package com.zeelauncher.core_data.usecase

import com.zeelauncher.core_data.repository.LibraryRepository
import javax.inject.Inject

internal class SyncLibraryUseCaseImpl @Inject constructor(
    private val libraryRepository: LibraryRepository,
) : SyncLibraryUseCase {

    override suspend fun invoke() {
        libraryRepository.syncLibrary()
    }
}
