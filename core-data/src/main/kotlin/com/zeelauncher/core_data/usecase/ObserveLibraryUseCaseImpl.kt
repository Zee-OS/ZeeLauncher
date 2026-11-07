package com.zeelauncher.core_data.usecase

import com.zeelauncher.core_data.repository.InstalledAppsRepository
import com.zeelauncher.core_data.repository.LibraryRepository
import com.zeelauncher.core_model.game.Game
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class ObserveLibraryUseCaseImpl @Inject constructor(
    private val libraryRepository: LibraryRepository,
    private val installedAppsRepository: InstalledAppsRepository,
) : ObserveLibraryUseCase {

    override fun invoke(): Flow<List<Game>> = channelFlow {
        launch {
            libraryRepository.syncLibrary()
            installedAppsRepository.observeChanges().collect {
                libraryRepository.syncLibrary()
            }
        }
        libraryRepository.observeLibrary().collect { send(it) }
    }
}
