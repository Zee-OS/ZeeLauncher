package com.zeelauncher.feature_library.viewmodel

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import com.zeelauncher.core_common.constant.ONE
import com.zeelauncher.core_common.constant.ZERO

@Immutable
data class LibraryGame(
    val title: String,
    val genre: String,
    @param:DrawableRes val cover: Int?,
)

sealed interface LibraryItem {
    data class Game(val game: LibraryGame) : LibraryItem
    data object Import : LibraryItem
}

@Immutable
data class LibraryUiState(
    val items: List<LibraryItem> = listOf(LibraryItem.Import),
    val selectedIndex: Int = ZERO,
) {
    val selectedItem: LibraryItem get() = items[selectedIndex]
    val hasGames: Boolean get() = items.size > ONE
}

sealed interface LibraryEffect {
    data class Launch(val title: String) : LibraryEffect
    data class AppInfo(val title: String) : LibraryEffect
    data object OpenImport : LibraryEffect
    data object Search : LibraryEffect
}
