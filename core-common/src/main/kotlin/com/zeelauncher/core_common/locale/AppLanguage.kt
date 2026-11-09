package com.zeelauncher.core_common.locale

import com.zeelauncher.core_common.attr.CommonAttribute

enum class AppLanguage(val tag: String, private val aliases: Set<String>) {
    English(CommonAttribute.AppLocale.ENGLISH_TAG, setOf(CommonAttribute.AppLocale.ENGLISH_TAG)),
    Indonesian(
        CommonAttribute.AppLocale.INDONESIAN_TAG,
        setOf(CommonAttribute.AppLocale.INDONESIAN_TAG, CommonAttribute.AppLocale.INDONESIAN_ALIAS_TAG),
    ),
    ;

    companion object {
        fun fromTag(tag: String?): AppLanguage = entries.firstOrNull { tag in it.aliases } ?: English
    }
}
