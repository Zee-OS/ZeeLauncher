package com.zeelauncher.core_data.usecase

import com.zeelauncher.core_data.repository.LibraryRepository
import com.zeelauncher.core_model.library.LibrarySyncResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class ObserveLibrarySyncResultsUseCaseImpl @Inject constructor(
    private val libraryRepository: LibraryRepository,
) : ObserveLibrarySyncResultsUseCase {

    override fun invoke(): Flow<LibrarySyncResult> = libraryRepository.observeSyncResults()
}
