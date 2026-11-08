package com.zeelauncher.core_data.usecase

interface RemoveGameFromLibraryUseCase {

    suspend operator fun invoke(packageName: String)
}
