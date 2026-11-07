package com.zeelauncher.core_data.usecase

import com.zeelauncher.core_data.repository.LibraryRepository
import com.zeelauncher.core_launch.AppLauncher
import com.zeelauncher.core_model.game.Game
import com.zeelauncher.core_model.launch.LaunchResult
import javax.inject.Inject

internal class LaunchGameUseCaseImpl @Inject constructor(
    private val appLauncher: AppLauncher,
    private val libraryRepository: LibraryRepository,
) : LaunchGameUseCase {

    override suspend fun invoke(game: Game): LaunchResult {
        val result = appLauncher.launch(game.packageName, game.activityName)
        if (result == LaunchResult.Success) libraryRepository.markPlayed(game.packageName)
        return result
    }
}
