package com.zeelauncher.core_data.usecase

import com.zeelauncher.core_common.locale.AppLanguage

interface GetAppLanguageUseCase {

    operator fun invoke(): AppLanguage
}
