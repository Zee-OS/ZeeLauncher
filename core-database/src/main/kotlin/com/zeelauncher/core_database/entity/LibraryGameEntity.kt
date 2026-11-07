package com.zeelauncher.core_database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

internal const val LIBRARY_GAME_TABLE = "library_game"
internal const val LIBRARY_GAME_PACKAGE_NAME = "package_name"
internal const val LIBRARY_GAME_ACTIVITY_NAME = "activity_name"
internal const val LIBRARY_GAME_LABEL = "label"
internal const val LIBRARY_GAME_LAST_PLAYED_AT = "last_played_at"

@Entity(tableName = LIBRARY_GAME_TABLE)
data class LibraryGameEntity(
    @PrimaryKey
    @ColumnInfo(name = LIBRARY_GAME_PACKAGE_NAME)
    val packageName: String,
    @ColumnInfo(name = LIBRARY_GAME_ACTIVITY_NAME)
    val activityName: String,
    @ColumnInfo(name = LIBRARY_GAME_LABEL)
    val label: String,
    @ColumnInfo(name = LIBRARY_GAME_LAST_PLAYED_AT)
    val lastPlayedAt: Long?,
)
