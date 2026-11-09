package com.zeelauncher.feature_library.viewmodel

import androidx.compose.runtime.Immutable
import com.zeelauncher.core_common.constant.ONE
import com.zeelauncher.core_common.constant.ZERO
import com.zeelauncher.core_model.app.InstalledApp
import com.zeelauncher.core_model.game.Game
import com.zeelauncher.feature_library.attr.LibraryFeatureAttribute

sealed interface LibraryItem {
    val key: String

    data class GameItem(val game: Game) : LibraryItem {
        override val key: String get() = game.packageName
    }

    data object ImportItem : LibraryItem {
        override val key: String get() = LibraryFeatureAttribute.UiState.IMPORT_ITEM_KEY
    }
}

@Immutable
data class ImportUiState(
    val isVisible: Boolean = false,
    val isLoading: Boolean = true,
    val apps: List<InstalledApp> = emptyList(),
    val selectedIndex: Int = ZERO,
)

enum class AppInfoOption { Play, Remove, Uninstall }

@Immutable
data class AppInfoUiState(
    val isVisible: Boolean = false,
    val game: Game? = null,
    val lastPlayed: RelativeTime = RelativeTime.Never,
    val versionName: String? = null,
    val selectedIndex: Int = ZERO,
) {
    val options: List<AppInfoOption> get() = AppInfoOption.entries
}

@Immutable
data class LibraryUiState(
    val isLoading: Boolean = true,
    val items: List<LibraryItem> = listOf(LibraryItem.ImportItem),
    val selectedIndex: Int = ZERO,
    val importDialog: ImportUiState = ImportUiState(),
    val appInfo: AppInfoUiState = AppInfoUiState(),
) {
    val selectedItem: LibraryItem get() = items[selectedIndex]
    val hasGames: Boolean get() = items.size > ONE
    val contentKey: List<String> get() = if (isLoading) emptyList() else items.map { it.key }
}

sealed interface LibraryEffect {
    data object GameNotFound : LibraryEffect
    data object GameImported : LibraryEffect
    data object GameRemoved : LibraryEffect
    data class LibraryUpdated(val addedCount: Int, val removedCount: Int) : LibraryEffect
}
