package com.zeelauncher.core_data.mapper

import com.zeelauncher.core_database.entity.LibraryGameEntity
import com.zeelauncher.core_model.app.InstalledApp
import com.zeelauncher.core_model.game.Game
import com.zeelauncher.core_model.game.GamePlatform

internal fun LibraryGameEntity.toGame(): Game = Game(
    packageName = packageName,
    activityName = activityName,
    label = label,
    platform = GamePlatform.Android,
    lastPlayedAt = lastPlayedAt,
)

internal fun InstalledApp.toLibraryGameEntity(lastPlayedAt: Long? = null): LibraryGameEntity = LibraryGameEntity(
    packageName = packageName,
    activityName = activityName,
    label = label,
    lastPlayedAt = lastPlayedAt,
)
