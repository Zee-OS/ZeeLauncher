package com.zeelauncher.core_common.text

import com.zeelauncher.core_common.attr.CommonAttribute
import com.zeelauncher.core_common.constant.EMPTY
import com.zeelauncher.core_common.constant.ONE

fun String.toAbbreviation(): String {
    val parts = split(CommonAttribute.Abbreviation.WordSeparator)
        .flatMap { it.split(CommonAttribute.Abbreviation.CamelCaseBoundary) }
        .filter { it.isNotEmpty() }
    val abbreviation = when {
        parts.isEmpty() -> EMPTY
        parts.size == ONE -> parts.first().take(CommonAttribute.Abbreviation.SINGLE_PART_LENGTH)
        else -> parts.take(CommonAttribute.Abbreviation.MAX_LENGTH).joinToString(EMPTY) { it.take(ONE) }
    }
    return abbreviation.uppercase()
}
