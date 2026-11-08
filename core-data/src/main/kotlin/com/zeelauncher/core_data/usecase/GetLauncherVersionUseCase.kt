package com.zeelauncher.core_data.usecase

interface GetLauncherVersionUseCase {

    suspend operator fun invoke(): String?
}
