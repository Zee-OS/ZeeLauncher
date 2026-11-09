package com.zeelauncher.core_database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zeelauncher.core_database.attr.DatabaseAttribute

@Entity(tableName = DatabaseAttribute.LibraryGame.TABLE)
data class LibraryGameEntity(
    @PrimaryKey
    @ColumnInfo(name = DatabaseAttribute.LibraryGame.COLUMN_PACKAGE_NAME)
    val packageName: String,
    @ColumnInfo(name = DatabaseAttribute.LibraryGame.COLUMN_ACTIVITY_NAME)
    val activityName: String,
    @ColumnInfo(name = DatabaseAttribute.LibraryGame.COLUMN_LABEL)
    val label: String,
    @ColumnInfo(name = DatabaseAttribute.LibraryGame.COLUMN_LAST_PLAYED_AT)
    val lastPlayedAt: Long?,
)
