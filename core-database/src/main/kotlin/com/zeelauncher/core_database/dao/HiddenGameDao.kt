package com.zeelauncher.core_database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.zeelauncher.core_database.entity.HIDDEN_GAME_PACKAGE_NAME
import com.zeelauncher.core_database.entity.HIDDEN_GAME_TABLE
import com.zeelauncher.core_database.entity.HiddenGameEntity

@Dao
interface HiddenGameDao {

    @Query("SELECT $HIDDEN_GAME_PACKAGE_NAME FROM $HIDDEN_GAME_TABLE")
    suspend fun getPackageNames(): List<String>

    @Upsert
    suspend fun upsert(entity: HiddenGameEntity)

    @Query("DELETE FROM $HIDDEN_GAME_TABLE WHERE $HIDDEN_GAME_PACKAGE_NAME = :packageName")
    suspend fun delete(packageName: String)
}
