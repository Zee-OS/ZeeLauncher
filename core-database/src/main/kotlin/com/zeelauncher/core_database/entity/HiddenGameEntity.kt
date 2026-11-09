package com.zeelauncher.core_database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zeelauncher.core_database.attr.DatabaseAttribute

@Entity(tableName = DatabaseAttribute.HiddenGame.TABLE)
data class HiddenGameEntity(
    @PrimaryKey
    @ColumnInfo(name = DatabaseAttribute.HiddenGame.COLUMN_PACKAGE_NAME)
    val packageName: String,
)
