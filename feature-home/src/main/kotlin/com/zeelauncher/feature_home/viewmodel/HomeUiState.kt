package com.zeelauncher.feature_home.viewmodel

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.zeelauncher.core_common.constant.ONE
import com.zeelauncher.core_common.constant.ZERO
import com.zeelauncher.feature_home.R

@Immutable
data class HomeGame(
    val title: String,
    val genre: String,
    @param:DrawableRes val cover: Int?,
)

sealed interface HomeCarouselItem {
    data class Game(val game: HomeGame) : HomeCarouselItem
    data object Import : HomeCarouselItem
}

enum class HomeTab(@param:StringRes val labelRes: Int) {
    Library(R.string.home_tab_library),
    Apps(R.string.home_tab_apps),
    Settings(R.string.home_tab_settings),
}

@Immutable
data class HomeUiState(
    val items: List<HomeCarouselItem> = listOf(HomeCarouselItem.Import),
    val selectedIndex: Int = ZERO,
    val tab: HomeTab = HomeTab.Library,
) {
    val selectedItem: HomeCarouselItem get() = items[selectedIndex]
    val hasGames: Boolean get() = items.size > ONE
}

sealed interface HomeEffect {
    data class Launch(val title: String) : HomeEffect
    data class AppInfo(val title: String) : HomeEffect
    data object OpenImport : HomeEffect
    data object Search : HomeEffect
}
