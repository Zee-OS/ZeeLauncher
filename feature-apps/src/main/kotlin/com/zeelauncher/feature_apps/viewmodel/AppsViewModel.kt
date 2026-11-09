package com.zeelauncher.feature_apps.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeelauncher.core_common.constant.MINUS_ONE
import com.zeelauncher.core_common.constant.ZERO
import com.zeelauncher.core_data.usecase.LaunchAppUseCase
import com.zeelauncher.core_data.usecase.ObserveInstalledAppsUseCase
import com.zeelauncher.core_input.ZeeAction
import com.zeelauncher.core_input.moveInGrid
import com.zeelauncher.core_model.app.InstalledApp
import com.zeelauncher.core_model.launch.LaunchResult
import com.zeelauncher.feature_apps.attr.AppsFeatureAttribute
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
import javax.inject.Inject

@HiltViewModel
class AppsViewModel @Inject constructor(
    observeInstalledAppsUseCase: ObserveInstalledAppsUseCase,
    private val launchAppUseCase: LaunchAppUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AppsUiState())
    val uiState: StateFlow<AppsUiState> = _uiState.asStateFlow()

    private val _effects = MutableSharedFlow<AppsEffect>(
        extraBufferCapacity = AppsFeatureAttribute.ViewModel.EFFECT_BUFFER_CAPACITY,
    )
    val effects: SharedFlow<AppsEffect> = _effects.asSharedFlow()

    init {
        observeInstalledAppsUseCase()
            .onEach { apps -> _uiState.update { it.withApps(apps) } }
            .launchIn(viewModelScope)
    }

    fun onAction(action: ZeeAction): Boolean {
        val state = _uiState.value
        if (state.isLoading || state.apps.isEmpty()) return false
        if (action == ZeeAction.Confirm) {
            open()
            return true
        }
        val index = action.moveInGrid(
            index = state.selectedIndex,
            size = state.apps.size,
            columns = AppsFeatureAttribute.Grid.COLUMNS,
        ) ?: return false
        _uiState.update { it.copy(selectedIndex = index) }
        return true
    }

    fun onTileClick(index: Int) {
        if (index == _uiState.value.selectedIndex) {
            open()
        } else {
            _uiState.update { it.copy(selectedIndex = index) }
        }
    }

    fun open() {
        val app = _uiState.value.selectedApp ?: return
        if (launchAppUseCase(app) == LaunchResult.AppNotFound) _effects.tryEmit(AppsEffect.AppNotFound)
    }

    private fun AppsUiState.withApps(newApps: List<InstalledApp>): AppsUiState {
        val previous = selectedApp
        val retained = previous?.let { app -> newApps.indexOfFirst { it.packageName == app.packageName } } ?: MINUS_ONE
        val newIndex = when {
            newApps.isEmpty() -> ZERO
            retained != MINUS_ONE -> retained
            else -> selectedIndex.coerceIn(ZERO, newApps.lastIndex)
        }
        return copy(isLoading = false, apps = newApps, selectedIndex = newIndex)
    }
}
