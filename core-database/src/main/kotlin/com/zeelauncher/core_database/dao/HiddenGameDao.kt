package com.zeelauncher.core_database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.zeelauncher.core_database.attr.DatabaseAttribute
import com.zeelauncher.core_database.entity.HiddenGameEntity

@Dao
interface HiddenGameDao {

    @Query("SELECT ${DatabaseAttribute.HiddenGame.COLUMN_PACKAGE_NAME} FROM ${DatabaseAttribute.HiddenGame.TABLE}")
    suspend fun getPackageNames(): List<String>

    @Upsert
    suspend fun upsert(entity: HiddenGameEntity)

    @Query(
        "DELETE FROM ${DatabaseAttribute.HiddenGame.TABLE} " +
            "WHERE ${DatabaseAttribute.HiddenGame.COLUMN_PACKAGE_NAME} = :packageName",
    )
    suspend fun delete(packageName: String)
}
