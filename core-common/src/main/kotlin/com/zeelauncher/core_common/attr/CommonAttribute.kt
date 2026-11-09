package com.zeelauncher.core_common.attr

internal object CommonAttribute {

    object Abbreviation {
        const val MAX_LENGTH = 3
        const val SINGLE_PART_LENGTH = 2
        val WordSeparator = Regex("[^\\p{L}\\p{N}]+")
        val CamelCaseBoundary = Regex("(?<=\\p{Ll})(?=\\p{Lu})")
    }

    object AppLocale {
        const val PREFERENCES_NAME = "app_locale"
        const val LANGUAGE_KEY = "language"
        const val ENGLISH_TAG = "en"
        const val INDONESIAN_TAG = "in"
        const val INDONESIAN_ALIAS_TAG = "id"
    }

    object Home {
        const val BUFFER_CAPACITY = 1
    }
}
