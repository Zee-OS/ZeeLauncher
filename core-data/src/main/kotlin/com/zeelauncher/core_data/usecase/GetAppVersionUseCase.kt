package com.zeelauncher.core_data.usecase

interface GetAppVersionUseCase {

    suspend operator fun invoke(packageName: String): String?
}
