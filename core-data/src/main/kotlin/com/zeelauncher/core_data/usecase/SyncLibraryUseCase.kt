package com.zeelauncher.core_data.usecase

import com.zeelauncher.core_model.library.LibrarySyncResult

interface SyncLibraryUseCase {

    suspend operator fun invoke(): LibrarySyncResult
}
