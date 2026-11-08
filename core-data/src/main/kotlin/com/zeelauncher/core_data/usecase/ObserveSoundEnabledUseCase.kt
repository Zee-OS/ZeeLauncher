package com.zeelauncher.core_data.usecase

import kotlinx.coroutines.flow.Flow

interface ObserveSoundEnabledUseCase {

    operator fun invoke(): Flow<Boolean>
}
