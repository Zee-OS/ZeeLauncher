package com.zeelauncher.core_data.usecase

import com.zeelauncher.core_model.game.Game
import com.zeelauncher.core_model.launch.LaunchResult

interface LaunchGameUseCase {

    suspend operator fun invoke(game: Game): LaunchResult
}
