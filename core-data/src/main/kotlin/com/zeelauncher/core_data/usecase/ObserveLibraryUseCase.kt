package com.zeelauncher.core_data.usecase

import com.zeelauncher.core_model.game.Game
import kotlinx.coroutines.flow.Flow

interface ObserveLibraryUseCase {

    operator fun invoke(): Flow<List<Game>>
}
