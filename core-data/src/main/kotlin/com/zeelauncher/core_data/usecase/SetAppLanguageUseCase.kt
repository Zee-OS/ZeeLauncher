package com.zeelauncher.core_data.usecase

import com.zeelauncher.core_common.locale.AppLanguage

interface SetAppLanguageUseCase {

    operator fun invoke(language: AppLanguage): Boolean
}
