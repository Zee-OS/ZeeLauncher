package com.zeelauncher.feature_library.viewmodel

import androidx.compose.runtime.Immutable
import com.zeelauncher.core_common.constant.ONE
import com.zeelauncher.core_common.constant.ZERO
import com.zeelauncher.core_model.game.Game

sealed interface LibraryItem {
    data class GameItem(val game: Game) : LibraryItem
    data object ImportItem : LibraryItem
}

@Immutable
data class LibraryUiState(
    val isLoading: Boolean = true,
    val items: List<LibraryItem> = listOf(LibraryItem.ImportItem),
    val selectedIndex: Int = ZERO,
) {
    val selectedItem: LibraryItem get() = items[selectedIndex]
    val hasGames: Boolean get() = items.size > ONE
}

sealed interface LibraryEffect {
    data object OpenImport : LibraryEffect
    data object GameNotFound : LibraryEffect
}
