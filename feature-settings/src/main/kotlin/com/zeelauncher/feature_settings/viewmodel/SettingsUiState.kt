package com.zeelauncher.feature_settings.viewmodel

import androidx.compose.runtime.Immutable
import com.zeelauncher.core_common.constant.ZERO
import com.zeelauncher.core_common.locale.AppLanguage

enum class SettingsRow { DefaultLauncher, Language, Sound, Licenses }

@Immutable
data class SettingsUiState(
    val rowIndex: Int = ZERO,
    val isDefaultLauncher: Boolean = false,
    val language: AppLanguage = AppLanguage.English,
    val soundEnabled: Boolean = true,
    val versionName: String? = null,
    val isLicensesVisible: Boolean = false,
) {
    val selectedRow: SettingsRow get() = SettingsRow.entries[rowIndex]
}

sealed interface SettingsEffect {
    data class OpenDefaultLauncher(val isDefault: Boolean) : SettingsEffect
    data object Recreate : SettingsEffect
    data object OpenSourceCode : SettingsEffect
}
