package com.zeelauncher.core_common.locale

import android.app.LocaleManager
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import androidx.core.content.edit
import com.zeelauncher.core_common.attr.CommonAttribute
import com.zeelauncher.core_common.constant.ZERO
import java.util.Locale

object AppLocale {

    fun current(context: Context): AppLanguage {
        val tag = selectedTag(context) ?: context.resources.configuration.locales[ZERO].language
        return AppLanguage.fromTag(tag)
    }

    fun select(context: Context, language: AppLanguage): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.getSystemService(LocaleManager::class.java).applicationLocales =
                LocaleList.forLanguageTags(language.tag)
            return false
        }
        preferences(context).edit { putString(CommonAttribute.AppLocale.LANGUAGE_KEY, language.tag) }
        return true
    }

    fun wrap(context: Context): Context {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) return context
        val tag = preferences(context).getString(CommonAttribute.AppLocale.LANGUAGE_KEY, null) ?: return context
        val configuration = Configuration(context.resources.configuration).apply {
            setLocale(Locale.forLanguageTag(tag))
        }
        return context.createConfigurationContext(configuration)
    }

    private fun selectedTag(context: Context): String? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val locales = context.getSystemService(LocaleManager::class.java).applicationLocales
            return if (locales.isEmpty) null else locales[ZERO].language
        }
        return preferences(context).getString(CommonAttribute.AppLocale.LANGUAGE_KEY, null)
    }

    private fun preferences(context: Context) =
        context.getSharedPreferences(CommonAttribute.AppLocale.PREFERENCES_NAME, Context.MODE_PRIVATE)
}
