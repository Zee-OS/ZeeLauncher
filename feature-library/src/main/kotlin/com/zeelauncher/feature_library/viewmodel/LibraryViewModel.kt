package com.zeelauncher.feature_library.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeelauncher.core_common.constant.MINUS_ONE
import com.zeelauncher.core_common.constant.ONE
import com.zeelauncher.core_common.constant.ZERO
import com.zeelauncher.core_common.home.HomeRequests
import com.zeelauncher.core_common.startup.AppReadiness
import com.zeelauncher.core_data.usecase.AddGameToLibraryUseCase
import com.zeelauncher.core_data.usecase.GetAppVersionUseCase
import com.zeelauncher.core_data.usecase.GetImportableAppsUseCase
import com.zeelauncher.core_data.usecase.LaunchGameUseCase
import com.zeelauncher.core_data.usecase.ObserveLibrarySyncResultsUseCase
import com.zeelauncher.core_data.usecase.ObserveLibraryUseCase
import com.zeelauncher.core_data.usecase.RemoveGameFromLibraryUseCase
import com.zeelauncher.core_data.usecase.SyncLibraryUseCase
import com.zeelauncher.core_data.usecase.UninstallAppUseCase
import com.zeelauncher.core_input.ZeeAction
import com.zeelauncher.core_input.moveInGrid
import com.zeelauncher.core_model.game.Game
import com.zeelauncher.core_model.launch.LaunchResult
import com.zeelauncher.feature_library.attr.LibraryFeatureAttribute
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

@HiltViewModel
class LibraryViewModel @Inject constructor(
    observeLibraryUseCase: ObserveLibraryUseCase,
    observeLibrarySyncResultsUseCase: ObserveLibrarySyncResultsUseCase,
    private val syncLibraryUseCase: SyncLibraryUseCase,
    private val launchGameUseCase: LaunchGameUseCase,
    private val getImportableAppsUseCase: GetImportableAppsUseCase,
    private val addGameToLibraryUseCase: AddGameToLibraryUseCase,
    private val getAppVersionUseCase: GetAppVersionUseCase,
    private val removeGameFromLibraryUseCase: RemoveGameFromLibraryUseCase,
    private val uninstallAppUseCase: UninstallAppUseCase,
    private val appReadiness: AppReadiness,
    homeRequests: HomeRequests,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LibraryUiState())
    val uiState: StateFlow<LibraryUiState> = _uiState.asStateFlow()

    private val _effects = MutableSharedFlow<LibraryEffect>(
        extraBufferCapacity = LibraryFeatureAttribute.ViewModel.EFFECT_BUFFER_CAPACITY,
    )
    val effects: SharedFlow<LibraryEffect> = _effects.asSharedFlow()

    private var pendingFocusPackageName: String? = null

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

        homeRequests.requests
            .onEach { closeOverlays() }
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
            ZeeAction.AppInfo -> {
                openAppInfo()
                true
            }
            ZeeAction.Up, ZeeAction.Down, ZeeAction.PreviousTab, ZeeAction.NextTab, ZeeAction.Back, ZeeAction.Menu, ZeeAction.Search -> false
        }
    }

    fun onSwipe(delta: Int): Boolean {
        val before = _uiState.value.selectedIndex
        moveSelection(delta)
        return _uiState.value.selectedIndex != before
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
            is LibraryItem.GameItem -> launchGame(item.game)
            LibraryItem.ImportItem -> openImport()
        }
    }

    fun onAppInfoClick() {
        openAppInfo()
    }

    fun onAppInfoAction(action: ZeeAction): Boolean {
        when (action) {
            ZeeAction.Up -> moveAppInfoSelection(MINUS_ONE)
            ZeeAction.Down -> moveAppInfoSelection(ONE)
            ZeeAction.Confirm -> selectAppInfoOption()
            ZeeAction.Back, ZeeAction.AppInfo -> closeAppInfo()
            ZeeAction.Left, ZeeAction.Right, ZeeAction.PreviousTab, ZeeAction.NextTab, ZeeAction.Menu, ZeeAction.Search -> Unit
        }
        return true
    }

    fun onAppInfoOptionClick(index: Int) {
        if (index == _uiState.value.appInfo.selectedIndex) {
            selectAppInfoOption()
        } else {
            _uiState.update { it.copy(appInfo = it.appInfo.copy(selectedIndex = index)) }
        }
    }

    fun closeAppInfo() {
        _uiState.update { it.copy(appInfo = it.appInfo.copy(isVisible = false)) }
    }

    fun onImportAction(action: ZeeAction): Boolean {
        when (action) {
            ZeeAction.Left, ZeeAction.Right, ZeeAction.Up, ZeeAction.Down -> moveImportSelection(action)
            ZeeAction.Confirm -> importSelectedApp()
            ZeeAction.Back -> closeImport()
            ZeeAction.PreviousTab, ZeeAction.NextTab, ZeeAction.Search, ZeeAction.AppInfo, ZeeAction.Menu -> Unit
        }
        return true
    }

    fun onImportTileClick(index: Int) {
        if (index == _uiState.value.importDialog.selectedIndex) {
            importSelectedApp()
        } else {
            _uiState.update { it.copy(importDialog = it.importDialog.copy(selectedIndex = index)) }
        }
    }

    fun closeImport() {
        _uiState.update { it.copy(importDialog = it.importDialog.copy(isVisible = false)) }
    }

    private fun closeOverlays() {
        _uiState.update {
            it.copy(
                importDialog = it.importDialog.copy(isVisible = false),
                appInfo = it.appInfo.copy(isVisible = false),
            )
        }
    }

    private fun openImport() {
        _uiState.update { it.copy(importDialog = ImportUiState(isVisible = true)) }
        viewModelScope.launch {
            val apps = getImportableAppsUseCase()
            _uiState.update {
                it.copy(importDialog = it.importDialog.copy(isLoading = false, apps = apps, selectedIndex = ZERO))
            }
        }
    }

    private fun importSelectedApp() {
        val dialog = _uiState.value.importDialog
        val app = dialog.apps.getOrNull(dialog.selectedIndex) ?: return
        pendingFocusPackageName = app.packageName
        closeImport()
        viewModelScope.launch {
            addGameToLibraryUseCase(app)
            _effects.emit(LibraryEffect.GameImported)
        }
    }

    private fun launchGame(game: Game) {
        viewModelScope.launch {
            val result = launchGameUseCase(game)
            if (result == LaunchResult.AppNotFound) _effects.emit(LibraryEffect.GameNotFound)
        }
    }

    private fun openAppInfo() {
        val game = (_uiState.value.selectedItem as? LibraryItem.GameItem)?.game ?: return
        _uiState.update {
            it.copy(
                appInfo = AppInfoUiState(
                    isVisible = true,
                    game = game,
                    lastPlayed = relativeTimeOf(game.lastPlayedAt, System.currentTimeMillis()),
                ),
            )
        }
        viewModelScope.launch {
            val versionName = getAppVersionUseCase(game.packageName)
            _uiState.update {
                if (it.appInfo.game?.packageName != game.packageName) return@update it
                it.copy(appInfo = it.appInfo.copy(versionName = versionName))
            }
        }
    }

    private fun selectAppInfoOption() {
        val appInfo = _uiState.value.appInfo
        val game = appInfo.game ?: return
        val option = appInfo.options.getOrNull(appInfo.selectedIndex) ?: return
        closeAppInfo()
        when (option) {
            AppInfoOption.Play -> launchGame(game)
            AppInfoOption.Remove -> viewModelScope.launch {
                removeGameFromLibraryUseCase(game.packageName)
                _effects.emit(LibraryEffect.GameRemoved)
            }
            AppInfoOption.Uninstall -> {
                val result = uninstallAppUseCase(game.packageName)
                if (result == LaunchResult.AppNotFound) _effects.tryEmit(LibraryEffect.GameNotFound)
            }
        }
    }

    private fun moveAppInfoSelection(delta: Int) {
        _uiState.update {
            val appInfo = it.appInfo
            val index = (appInfo.selectedIndex + delta).coerceIn(ZERO, appInfo.options.lastIndex)
            it.copy(appInfo = appInfo.copy(selectedIndex = index))
        }
    }

    private fun moveImportSelection(action: ZeeAction) {
        _uiState.update {
            val dialog = it.importDialog
            if (dialog.apps.isEmpty()) return@update it
            val index = action.moveInGrid(
                index = dialog.selectedIndex,
                size = dialog.apps.size,
                columns = LibraryFeatureAttribute.Import.GRID_COLUMNS,
            ) ?: return@update it
            it.copy(importDialog = dialog.copy(selectedIndex = index))
        }
    }

    private fun moveSelection(delta: Int) {
        _uiState.update {
            it.copy(selectedIndex = (it.selectedIndex + delta).coerceIn(ZERO, it.items.lastIndex))
        }
    }

    private fun LibraryUiState.withGames(games: List<Game>): LibraryUiState {
        val newItems = games.map { LibraryItem.GameItem(it) } + LibraryItem.ImportItem
        val pendingIndex = pendingIndex(newItems)
        val newIndex = when {
            isLoading -> lastPlayedIndex(games)
            pendingIndex != MINUS_ONE -> pendingIndex.also { pendingFocusPackageName = null }
            else -> retainedIndex(newItems)
        }
        return copy(isLoading = false, items = newItems, selectedIndex = newIndex)
    }

    private fun pendingIndex(newItems: List<LibraryItem>): Int {
        val packageName = pendingFocusPackageName ?: return MINUS_ONE
        return newItems.indexOfFirst { it is LibraryItem.GameItem && it.game.packageName == packageName }
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
