package com.zeelauncher.feature_apps.viewmodel

import androidx.compose.runtime.Immutable
import com.zeelauncher.core_common.constant.ZERO
import com.zeelauncher.core_model.app.InstalledApp

@Immutable
data class AppsUiState(
    val isLoading: Boolean = true,
    val apps: List<InstalledApp> = emptyList(),
    val selectedIndex: Int = ZERO,
) {
    val selectedApp: InstalledApp? get() = apps.getOrNull(selectedIndex)
}

sealed interface AppsEffect {
    data object AppNotFound : AppsEffect
}
