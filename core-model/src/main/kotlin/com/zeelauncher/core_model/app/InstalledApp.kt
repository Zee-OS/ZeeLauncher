package com.zeelauncher.core_model.app

data class InstalledApp(
    val packageName: String,
    val activityName: String,
    val label: String,
    val isGame: Boolean,
)