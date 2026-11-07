package com.zeelauncher.core_database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

internal const val HIDDEN_GAME_TABLE = "hidden_game"
internal const val HIDDEN_GAME_PACKAGE_NAME = "package_name"

@Entity(tableName = HIDDEN_GAME_TABLE)
data class HiddenGameEntity(
    @PrimaryKey
    @ColumnInfo(name = HIDDEN_GAME_PACKAGE_NAME)
    val packageName: String,
)
