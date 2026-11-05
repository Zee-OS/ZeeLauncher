package com.dwiaziprasetya.zeelauncher.shell

import androidx.annotation.StringRes
import com.dwiaziprasetya.zeelauncher.R
import com.zeelauncher.feature_apps.navigation.AppsDestination
import com.zeelauncher.feature_library.navigation.LibraryDestination
import com.zeelauncher.feature_settings.navigation.SettingsDestination

internal enum class MainTab(
    @param:StringRes val labelRes: Int,
    val destination: Any,
) {
    Library(R.string.main_tab_library, LibraryDestination),
    Apps(R.string.main_tab_apps, AppsDestination),
    Settings(R.string.main_tab_settings, SettingsDestination),
}
