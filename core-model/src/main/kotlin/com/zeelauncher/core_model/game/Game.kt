package com.zeelauncher.core_model.game

data class Game(
    val packageName: String,
    val activityName: String,
    val label: String,
    val platform: GamePlatform,
    val lastPlayedAt: Long?,
)