package com.zeelauncher.feature_home.viewmodel

import androidx.lifecycle.ViewModel
import com.zeelauncher.core_common.constant.MINUS_ONE
import com.zeelauncher.core_common.constant.ONE
import com.zeelauncher.core_common.constant.ZERO
import com.zeelauncher.core_input.ZeeAction
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

private const val EFFECT_BUFFER_CAPACITY = 8

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(initialState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _effects = MutableSharedFlow<HomeEffect>(extraBufferCapacity = EFFECT_BUFFER_CAPACITY)
    val effects: SharedFlow<HomeEffect> = _effects.asSharedFlow()

    fun onAction(action: ZeeAction) {
        val onLibrary = _uiState.value.tab == HomeTab.Library
        when (action) {
            ZeeAction.Left -> if (onLibrary) moveSelection(MINUS_ONE)
            ZeeAction.Right -> if (onLibrary) moveSelection(ONE)
            ZeeAction.PreviousTab -> moveTab(MINUS_ONE)
            ZeeAction.NextTab -> moveTab(ONE)
            ZeeAction.Confirm -> if (onLibrary) confirm()
            ZeeAction.Back -> if (!onLibrary) onTabClick(HomeTab.Library)
            ZeeAction.Search -> _effects.tryEmit(HomeEffect.Search)
        }
    }

    fun onCardClick(index: Int) {
        if (index == _uiState.value.selectedIndex) {
            confirm()
        } else {
            _uiState.update { it.copy(selectedIndex = index) }
        }
    }

    fun onTabClick(tab: HomeTab) {
        _uiState.update { it.copy(tab = tab) }
    }

    fun onAppInfoClick() {
        val item = _uiState.value.selectedItem
        if (item is HomeCarouselItem.Game) _effects.tryEmit(HomeEffect.AppInfo(item.game.title))
    }

    fun confirm() {
        val effect = when (val item = _uiState.value.selectedItem) {
            is HomeCarouselItem.Game -> HomeEffect.Launch(item.game.title)
            HomeCarouselItem.Import -> HomeEffect.OpenImport
        }
        _effects.tryEmit(effect)
    }

    private fun moveSelection(delta: Int) {
        _uiState.update {
            it.copy(selectedIndex = (it.selectedIndex + delta).coerceIn(ZERO, it.items.lastIndex))
        }
    }

    private fun moveTab(delta: Int) {
        val tabs = HomeTab.entries
        _uiState.update {
            it.copy(tab = tabs[(it.tab.ordinal + delta).coerceIn(ZERO, tabs.lastIndex)])
        }
    }

    private fun initialState(): HomeUiState {
        val items = demoGames().map { HomeCarouselItem.Game(it) } + HomeCarouselItem.Import
        val selected = items.indexOfFirst {
            it is HomeCarouselItem.Game && it.game == DefaultSelectedGame
        }
        return HomeUiState(items = items, selectedIndex = selected.coerceAtLeast(ZERO))
    }
}
