package com.zeelauncher.core_data.usecase

import android.content.Context
import com.zeelauncher.core_common.locale.AppLanguage
import com.zeelauncher.core_common.locale.AppLocale
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal class SetAppLanguageUseCaseImpl @Inject constructor(
    @param:ApplicationContext private val context: Context,
) : SetAppLanguageUseCase {

    override fun invoke(language: AppLanguage): Boolean = AppLocale.select(context, language)
}
