package com.zeelauncher.core_input

import com.zeelauncher.core_common.constant.ONE
import com.zeelauncher.core_common.constant.ZERO

fun ZeeAction.moveInGrid(index: Int, size: Int, columns: Int): Int? = when (this) {
    ZeeAction.Left -> if (index % columns != ZERO) index - ONE else index
    ZeeAction.Right -> {
        val isLastColumn = index % columns == columns - ONE
        if (!isLastColumn && index + ONE < size) index + ONE else index
    }
    ZeeAction.Up -> if (index - columns >= ZERO) index - columns else index
    ZeeAction.Down -> {
        val lastRow = (size - ONE) / columns
        when {
            index + columns < size -> index + columns
            index / columns < lastRow -> size - ONE
            else -> index
        }
    }
    ZeeAction.PreviousTab, ZeeAction.NextTab, ZeeAction.Confirm, ZeeAction.Back, ZeeAction.AppInfo, ZeeAction.Menu, ZeeAction.Search -> null
}
