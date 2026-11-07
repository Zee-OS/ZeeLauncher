package com.zeelauncher.core_data.usecase

import com.zeelauncher.core_model.library.LibrarySyncResult
import kotlinx.coroutines.flow.Flow

interface ObserveLibrarySyncResultsUseCase {

    operator fun invoke(): Flow<LibrarySyncResult>
}
