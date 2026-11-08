package com.zeelauncher.core_common.locale

private const val ENGLISH_TAG = "en"
private const val INDONESIAN_TAG = "in"
private const val INDONESIAN_ALIAS_TAG = "id"

enum class AppLanguage(val tag: String, private val aliases: Set<String>) {
    English(ENGLISH_TAG, setOf(ENGLISH_TAG)),
    Indonesian(INDONESIAN_TAG, setOf(INDONESIAN_TAG, INDONESIAN_ALIAS_TAG)),
    ;

    companion object {
        fun fromTag(tag: String?): AppLanguage = entries.firstOrNull { tag in it.aliases } ?: English
    }
}
