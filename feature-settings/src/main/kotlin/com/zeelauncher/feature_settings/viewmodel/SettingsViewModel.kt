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
import com.zeelauncher.core_data.usecase.SyncLibraryUseCase
import com.zeelauncher.core_input.ZeeAction
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

private const val EFFECT_BUFFER_CAPACITY = 4

@HiltViewModel
class SettingsViewModel @Inject constructor(
    observeSoundEnabledUseCase: ObserveSoundEnabledUseCase,
    getLauncherVersionUseCase: GetLauncherVersionUseCase,
    private val setSoundEnabledUseCase: SetSoundEnabledUseCase,
    private val getAppLanguageUseCase: GetAppLanguageUseCase,
    private val setAppLanguageUseCase: SetAppLanguageUseCase,
    private val isDefaultLauncherUseCase: IsDefaultLauncherUseCase,
    private val syncLibraryUseCase: SyncLibraryUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    private val _effects = MutableSharedFlow<SettingsEffect>(extraBufferCapacity = EFFECT_BUFFER_CAPACITY)
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

    fun onAction(action: ZeeAction): Boolean = when (_uiState.value.focus) {
        SettingsFocus.Sidebar -> onSidebarAction(action)
        SettingsFocus.Panel -> onPanelAction(action)
    }

    fun onLicensesAction(action: ZeeAction): Boolean {
        if (action == ZeeAction.Back || action == ZeeAction.Confirm) closeLicenses()
        return true
    }

    fun onSectionClick(section: SettingsSection) {
        _uiState.update { it.copy(section = section, focus = SettingsFocus.Sidebar, rowIndex = ZERO) }
    }

    fun onRowClick(row: SettingsRow) {
        focusRow(row)
        activate(row)
    }

    fun onLanguageClick(language: AppLanguage) {
        focusRow(SettingsRow.Language)
        selectLanguage(language)
    }

    fun closeLicenses() {
        _uiState.update { it.copy(isLicensesVisible = false) }
    }

    private fun onSidebarAction(action: ZeeAction): Boolean = when (action) {
        ZeeAction.Up -> {
            moveSection(MINUS_ONE)
            true
        }
        ZeeAction.Down -> {
            moveSection(ONE)
            true
        }
        ZeeAction.Right, ZeeAction.Confirm -> {
            _uiState.update { it.copy(focus = SettingsFocus.Panel, rowIndex = ZERO) }
            true
        }
        ZeeAction.Left, ZeeAction.PreviousTab, ZeeAction.NextTab, ZeeAction.Back, ZeeAction.AppInfo, ZeeAction.Search -> false
    }

    private fun onPanelAction(action: ZeeAction): Boolean {
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
            ZeeAction.Left -> {
                val previous = AppLanguage.entries.getOrNull(state.language.ordinal - ONE)
                if (isLanguageRow && previous != null) selectLanguage(previous) else focusSidebar()
                true
            }
            ZeeAction.Right -> {
                val next = AppLanguage.entries.getOrNull(state.language.ordinal + ONE)
                if (isLanguageRow && next != null) selectLanguage(next)
                true
            }
            ZeeAction.Back -> {
                focusSidebar()
                true
            }
            ZeeAction.PreviousTab, ZeeAction.NextTab, ZeeAction.AppInfo, ZeeAction.Search -> false
        }
    }

    private fun activate(row: SettingsRow) {
        val state = _uiState.value
        when (row) {
            SettingsRow.DefaultLauncher -> _effects.tryEmit(SettingsEffect.OpenDefaultLauncher(state.isDefaultLauncher))
            SettingsRow.Language -> selectLanguage(AppLanguage.entries[(state.language.ordinal + ONE) % AppLanguage.entries.size])
            SettingsRow.Sound -> viewModelScope.launch { setSoundEnabledUseCase(!state.soundEnabled) }
            SettingsRow.Rescan -> rescan()
            SettingsRow.Version -> Unit
            SettingsRow.Licenses -> _uiState.update { it.copy(isLicensesVisible = true) }
        }
    }

    private fun selectLanguage(language: AppLanguage) {
        if (language == _uiState.value.language) return
        val needsRecreate = setAppLanguageUseCase(language)
        _uiState.update { it.copy(language = language) }
        if (needsRecreate) _effects.tryEmit(SettingsEffect.Recreate)
    }

    private fun rescan() {
        if (_uiState.value.isRescanning) return
        _uiState.update { it.copy(isRescanning = true) }
        viewModelScope.launch {
            val result = syncLibraryUseCase()
            _uiState.update { it.copy(isRescanning = false) }
            _effects.emit(SettingsEffect.LibraryRescanned(result))
        }
    }

    private fun focusRow(row: SettingsRow) {
        _uiState.update {
            it.copy(focus = SettingsFocus.Panel, rowIndex = it.section.rows.indexOf(row).coerceAtLeast(ZERO))
        }
    }

    private fun focusSidebar() {
        _uiState.update { it.copy(focus = SettingsFocus.Sidebar) }
    }

    private fun moveSection(delta: Int) {
        _uiState.update {
            val sections = SettingsSection.entries
            val index = (it.section.ordinal + delta).coerceIn(ZERO, sections.lastIndex)
            it.copy(section = sections[index], rowIndex = ZERO)
        }
    }

    private fun moveRow(delta: Int) {
        _uiState.update {
            it.copy(rowIndex = (it.rowIndex + delta).coerceIn(ZERO, it.section.rows.lastIndex))
        }
    }
}
