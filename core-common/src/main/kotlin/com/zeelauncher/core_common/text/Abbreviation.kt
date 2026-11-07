package com.zeelauncher.core_common.text

import com.zeelauncher.core_common.constant.EMPTY
import com.zeelauncher.core_common.constant.ONE

private const val MAX_ABBREVIATION_LENGTH = 3
private const val SINGLE_PART_ABBREVIATION_LENGTH = 2
private val WordSeparator = Regex("[^\\p{L}\\p{N}]+")
private val CamelCaseBoundary = Regex("(?<=\\p{Ll})(?=\\p{Lu})")

fun String.toAbbreviation(): String {
    val parts = split(WordSeparator)
        .flatMap { it.split(CamelCaseBoundary) }
        .filter { it.isNotEmpty() }
    val abbreviation = when {
        parts.isEmpty() -> EMPTY
        parts.size == ONE -> parts.first().take(SINGLE_PART_ABBREVIATION_LENGTH)
        else -> parts.take(MAX_ABBREVIATION_LENGTH).joinToString(EMPTY) { it.take(ONE) }
    }
    return abbreviation.uppercase()
}
