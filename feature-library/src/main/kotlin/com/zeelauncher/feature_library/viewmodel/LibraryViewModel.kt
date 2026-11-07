package com.zeelauncher.feature_library.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeelauncher.core_common.constant.MINUS_ONE
import com.zeelauncher.core_common.constant.ONE
import com.zeelauncher.core_common.constant.ZERO
import com.zeelauncher.core_common.startup.AppReadiness
import com.zeelauncher.core_data.usecase.LaunchGameUseCase
import com.zeelauncher.core_data.usecase.ObserveLibrarySyncResultsUseCase
import com.zeelauncher.core_data.usecase.ObserveLibraryUseCase
import com.zeelauncher.core_data.usecase.OpenAppInfoUseCase
import com.zeelauncher.core_data.usecase.SyncLibraryUseCase
import com.zeelauncher.core_input.ZeeAction
import com.zeelauncher.core_model.game.Game
import com.zeelauncher.core_model.launch.LaunchResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val EFFECT_BUFFER_CAPACITY = 8

@HiltViewModel
class LibraryViewModel @Inject constructor(
    observeLibraryUseCase: ObserveLibraryUseCase,
    observeLibrarySyncResultsUseCase: ObserveLibrarySyncResultsUseCase,
    private val syncLibraryUseCase: SyncLibraryUseCase,
    private val launchGameUseCase: LaunchGameUseCase,
    private val openAppInfoUseCase: OpenAppInfoUseCase,
    private val appReadiness: AppReadiness,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LibraryUiState())
    val uiState: StateFlow<LibraryUiState> = _uiState.asStateFlow()

    private val _effects = MutableSharedFlow<LibraryEffect>(extraBufferCapacity = EFFECT_BUFFER_CAPACITY)
    val effects: SharedFlow<LibraryEffect> = _effects.asSharedFlow()

    init {
        observeLibraryUseCase()
            .onEach { games ->
                _uiState.update { it.withGames(games) }
                appReadiness.markReady()
            }
            .launchIn(viewModelScope)

        observeLibrarySyncResultsUseCase()
            .onEach { result ->
                _effects.emit(LibraryEffect.LibraryUpdated(result.addedCount, result.removedCount))
            }
            .launchIn(viewModelScope)
    }

    fun onStart() {
        viewModelScope.launch { syncLibraryUseCase() }
    }

    fun onAction(action: ZeeAction): Boolean {
        if (_uiState.value.isLoading) return false
        return when (action) {
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
            ZeeAction.Up, ZeeAction.Down, ZeeAction.PreviousTab, ZeeAction.NextTab, ZeeAction.Back, ZeeAction.Search -> false
        }
    }

    fun onCardClick(index: Int) {
        if (index == _uiState.value.selectedIndex) {
            confirm()
        } else {
            _uiState.update { it.copy(selectedIndex = index) }
        }
    }

    fun confirm() {
        when (val item = _uiState.value.selectedItem) {
            is LibraryItem.GameItem -> viewModelScope.launch {
                val result = launchGameUseCase(item.game)
                if (result == LaunchResult.AppNotFound) _effects.emit(LibraryEffect.GameNotFound)
            }
            LibraryItem.ImportItem -> _effects.tryEmit(LibraryEffect.OpenImport)
        }
    }

    fun onAppInfoClick() {
        val item = _uiState.value.selectedItem as? LibraryItem.GameItem ?: return
        val result = openAppInfoUseCase(item.game.packageName, item.game.activityName)
        if (result == LaunchResult.AppNotFound) _effects.tryEmit(LibraryEffect.GameNotFound)
    }

    private fun moveSelection(delta: Int) {
        _uiState.update {
            it.copy(selectedIndex = (it.selectedIndex + delta).coerceIn(ZERO, it.items.lastIndex))
        }
    }

    private fun LibraryUiState.withGames(games: List<Game>): LibraryUiState {
        val newItems = games.map { LibraryItem.GameItem(it) } + LibraryItem.ImportItem
        val newIndex = if (isLoading) lastPlayedIndex(games) else retainedIndex(newItems)
        return copy(isLoading = false, items = newItems, selectedIndex = newIndex)
    }

    private fun lastPlayedIndex(games: List<Game>): Int =
        games.indices.maxByOrNull { games[it].lastPlayedAt ?: Long.MIN_VALUE } ?: ZERO

    private fun LibraryUiState.retainedIndex(newItems: List<LibraryItem>): Int {
        val previous = selectedItem as? LibraryItem.GameItem ?: return newItems.lastIndex
        val index = newItems.indexOfFirst {
            it is LibraryItem.GameItem && it.game.packageName == previous.game.packageName
        }
        return if (index != MINUS_ONE) index else selectedIndex.coerceIn(ZERO, newItems.lastIndex)
    }
}
