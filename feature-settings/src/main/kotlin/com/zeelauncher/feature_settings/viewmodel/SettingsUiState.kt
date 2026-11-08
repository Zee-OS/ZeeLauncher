package com.zeelauncher.feature_settings.viewmodel

import androidx.compose.runtime.Immutable
import com.zeelauncher.core_common.constant.ZERO
import com.zeelauncher.core_common.locale.AppLanguage
import com.zeelauncher.core_model.library.LibrarySyncResult

enum class SettingsRow { DefaultLauncher, Language, Sound, Rescan, Version, Licenses }

enum class SettingsSection(val rows: List<SettingsRow>) {
    General(listOf(SettingsRow.DefaultLauncher, SettingsRow.Language, SettingsRow.Sound)),
    Library(listOf(SettingsRow.Rescan)),
    About(listOf(SettingsRow.Version, SettingsRow.Licenses)),
}

enum class SettingsFocus { Sidebar, Panel }

@Immutable
data class SettingsUiState(
    val section: SettingsSection = SettingsSection.General,
    val focus: SettingsFocus = SettingsFocus.Sidebar,
    val rowIndex: Int = ZERO,
    val isDefaultLauncher: Boolean = false,
    val language: AppLanguage = AppLanguage.English,
    val soundEnabled: Boolean = true,
    val versionName: String? = null,
    val isRescanning: Boolean = false,
    val isLicensesVisible: Boolean = false,
) {
    val selectedRow: SettingsRow get() = section.rows[rowIndex]
    val isPanelFocused: Boolean get() = focus == SettingsFocus.Panel
}

sealed interface SettingsEffect {
    data class OpenDefaultLauncher(val isDefault: Boolean) : SettingsEffect
    data object Recreate : SettingsEffect
    data class LibraryRescanned(val result: LibrarySyncResult) : SettingsEffect
}
