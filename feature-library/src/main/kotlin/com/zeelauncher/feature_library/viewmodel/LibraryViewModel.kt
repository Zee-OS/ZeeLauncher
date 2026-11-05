package com.zeelauncher.feature_library.viewmodel

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

class LibraryViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(initialState())
    val uiState: StateFlow<LibraryUiState> = _uiState.asStateFlow()

    private val _effects = MutableSharedFlow<LibraryEffect>(extraBufferCapacity = EFFECT_BUFFER_CAPACITY)
    val effects: SharedFlow<LibraryEffect> = _effects.asSharedFlow()

    fun onAction(action: ZeeAction): Boolean = when (action) {
        ZeeAction.Left -> {
            moveSelection(MINUS_ONE)
            true
        }
        ZeeAction.Right -> {
            moveSelection(ONE)
            true
        }
        ZeeAction.Confirm -> {
            confirm()
            true
        }
        ZeeAction.Search -> {
            _effects.tryEmit(LibraryEffect.Search)
            true
        }
        ZeeAction.PreviousTab, ZeeAction.NextTab, ZeeAction.Back -> false
    }

    fun onCardClick(index: Int) {
        if (index == _uiState.value.selectedIndex) {
            confirm()
        } else {
            _uiState.update { it.copy(selectedIndex = index) }
        }
    }

    fun onAppInfoClick() {
        val item = _uiState.value.selectedItem
        if (item is LibraryItem.Game) _effects.tryEmit(LibraryEffect.AppInfo(item.game.title))
    }

    fun confirm() {
        val effect = when (val item = _uiState.value.selectedItem) {
            is LibraryItem.Game -> LibraryEffect.Launch(item.game.title)
            LibraryItem.Import -> LibraryEffect.OpenImport
        }
        _effects.tryEmit(effect)
    }

    private fun moveSelection(delta: Int) {
        _uiState.update {
            it.copy(selectedIndex = (it.selectedIndex + delta).coerceIn(ZERO, it.items.lastIndex))
        }
    }

    private fun initialState(): LibraryUiState {
        val items = demoGames().map { LibraryItem.Game(it) } + LibraryItem.Import
        val selected = items.indexOfFirst {
            it is LibraryItem.Game && it.game == DefaultSelectedGame
        }
        return LibraryUiState(items = items, selectedIndex = selected.coerceAtLeast(ZERO))
    }
}
