package com.zeelauncher.feature_settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeelauncher.core_common.constant.MINUS_ONE
import com.zeelauncher.core_common.constant.ONE
import com.zeelauncher.core_common.constant.ZERO
import com.zeelauncher.core_common.locale.AppLanguage
import com.zeelauncher.core_data.usecase.GetAppLanguageUseCase
import com.zeelauncher.core_data.usecase.GetLauncherVersionUseCase
import com.zeelauncher.core_data.usecase.IsDefaultLauncherUseCase
import com.zeelauncher.core_data.usecase.ObserveSoundEnabledUseCase
import com.zeelauncher.core_data.usecase.SetAppLanguageUseCase
import com.zeelauncher.core_data.usecase.SetSoundEnabledUseCase
import com.zeelauncher.core_input.ZeeAction
import com.zeelauncher.feature_settings.attr.SettingsFeatureAttribute
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
class SettingsViewModel @Inject constructor(
    observeSoundEnabledUseCase: ObserveSoundEnabledUseCase,
    getLauncherVersionUseCase: GetLauncherVersionUseCase,
    private val setSoundEnabledUseCase: SetSoundEnabledUseCase,
    private val getAppLanguageUseCase: GetAppLanguageUseCase,
    private val setAppLanguageUseCase: SetAppLanguageUseCase,
    private val isDefaultLauncherUseCase: IsDefaultLauncherUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    private val _effects = MutableSharedFlow<SettingsEffect>(
        extraBufferCapacity = SettingsFeatureAttribute.ViewModel.EFFECT_BUFFER_CAPACITY,
    )
    val effects: SharedFlow<SettingsEffect> = _effects.asSharedFlow()

    init {
        observeSoundEnabledUseCase()
            .onEach { enabled -> _uiState.update { it.copy(soundEnabled = enabled) } }
            .launchIn(viewModelScope)

        viewModelScope.launch {
            val versionName = getLauncherVersionUseCase()
            _uiState.update { it.copy(versionName = versionName) }
        }
        refresh()
    }

    fun refresh() {
        _uiState.update {
            it.copy(isDefaultLauncher = isDefaultLauncherUseCase(), language = getAppLanguageUseCase())
        }
    }

    fun onAction(action: ZeeAction): Boolean {
        val state = _uiState.value
        val isLanguageRow = state.selectedRow == SettingsRow.Language
        return when (action) {
            ZeeAction.Up -> {
                moveRow(MINUS_ONE)
                true
            }
            ZeeAction.Down -> {
                moveRow(ONE)
                true
            }
            ZeeAction.Confirm -> {
                activate(state.selectedRow)
                true
            }
            ZeeAction.Left -> isLanguageRow && stepLanguage(MINUS_ONE)
            ZeeAction.Right -> isLanguageRow && stepLanguage(ONE)
            ZeeAction.Back, ZeeAction.PreviousTab, ZeeAction.NextTab, ZeeAction.AppInfo, ZeeAction.Menu, ZeeAction.Search -> false
        }
    }

    fun onLicensesAction(action: ZeeAction): Boolean {
        when (action) {
            ZeeAction.Confirm -> openSourceCode()
            ZeeAction.Back -> closeLicenses()
            else -> Unit
        }
        return true
    }

    fun onRowClick(row: SettingsRow) {
        focusRow(row)
        activate(row)
    }

    fun onLanguageClick(language: AppLanguage) {
        focusRow(SettingsRow.Language)
        selectLanguage(language)
    }

    fun openSourceCode() {
        closeLicenses()
        _effects.tryEmit(SettingsEffect.OpenSourceCode)
    }

    fun closeLicenses() {
        _uiState.update { it.copy(isLicensesVisible = false) }
    }

    private fun activate(row: SettingsRow) {
        val state = _uiState.value
        when (row) {
            SettingsRow.DefaultLauncher -> _effects.tryEmit(SettingsEffect.OpenDefaultLauncher(state.isDefaultLauncher))
            SettingsRow.Language -> selectLanguage(AppLanguage.entries[(state.language.ordinal + ONE) % AppLanguage.entries.size])
            SettingsRow.Sound -> viewModelScope.launch { setSoundEnabledUseCase(!state.soundEnabled) }
            SettingsRow.Licenses -> _uiState.update { it.copy(isLicensesVisible = true) }
        }
    }

    private fun stepLanguage(delta: Int): Boolean {
        val target = AppLanguage.entries.getOrNull(_uiState.value.language.ordinal + delta) ?: return false
        selectLanguage(target)
        return true
    }

    private fun selectLanguage(language: AppLanguage) {
        if (language == _uiState.value.language) return
        val needsRecreate = setAppLanguageUseCase(language)
        _uiState.update { it.copy(language = language) }
        if (needsRecreate) _effects.tryEmit(SettingsEffect.Recreate)
    }

    private fun focusRow(row: SettingsRow) {
        _uiState.update { it.copy(rowIndex = row.ordinal) }
    }

    private fun moveRow(delta: Int) {
        _uiState.update {
            it.copy(rowIndex = (it.rowIndex + delta).coerceIn(ZERO, SettingsRow.entries.lastIndex))
        }
    }
}
