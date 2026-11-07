package com.zeelauncher.feature_library.viewmodel

import androidx.compose.runtime.Immutable
import com.zeelauncher.core_common.constant.ONE
import com.zeelauncher.core_common.constant.ZERO
import com.zeelauncher.core_model.game.Game

private const val IMPORT_ITEM_KEY = "import"

sealed interface LibraryItem {
    val key: String

    data class GameItem(val game: Game) : LibraryItem {
        override val key: String get() = game.packageName
    }

    data object ImportItem : LibraryItem {
        override val key: String get() = IMPORT_ITEM_KEY
    }
}

@Immutable
data class LibraryUiState(
    val isLoading: Boolean = true,
    val items: List<LibraryItem> = listOf(LibraryItem.ImportItem),
    val selectedIndex: Int = ZERO,
) {
    val selectedItem: LibraryItem get() = items[selectedIndex]
    val hasGames: Boolean get() = items.size > ONE
    val contentKey: List<String> get() = if (isLoading) emptyList() else items.map { it.key }
}

sealed interface LibraryEffect {
    data object OpenImport : LibraryEffect
    data object GameNotFound : LibraryEffect
}
