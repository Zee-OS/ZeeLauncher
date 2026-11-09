package com.zeelauncher.core_database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.zeelauncher.core_database.attr.DatabaseAttribute
import com.zeelauncher.core_database.entity.LibraryGameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LibraryGameDao {

    @Query(
        "SELECT * FROM ${DatabaseAttribute.LibraryGame.TABLE} " +
            "ORDER BY ${DatabaseAttribute.LibraryGame.COLUMN_LABEL} COLLATE NOCASE ASC",
    )
    fun observeAll(): Flow<List<LibraryGameEntity>>

    @Query("SELECT * FROM ${DatabaseAttribute.LibraryGame.TABLE}")
    suspend fun getAll(): List<LibraryGameEntity>

    @Upsert
    suspend fun upsert(entities: List<LibraryGameEntity>)

    @Query(
        "DELETE FROM ${DatabaseAttribute.LibraryGame.TABLE} " +
            "WHERE ${DatabaseAttribute.LibraryGame.COLUMN_PACKAGE_NAME} IN (:packageNames)",
    )
    suspend fun delete(packageNames: List<String>)

    @Query(
        "UPDATE ${DatabaseAttribute.LibraryGame.TABLE} " +
            "SET ${DatabaseAttribute.LibraryGame.COLUMN_LAST_PLAYED_AT} = :playedAt " +
            "WHERE ${DatabaseAttribute.LibraryGame.COLUMN_PACKAGE_NAME} = :packageName",
    )
    suspend fun updateLastPlayed(packageName: String, playedAt: Long)

    @Transaction
    suspend fun sync(removedPackageNames: List<String>, upserts: List<LibraryGameEntity>) {
        delete(removedPackageNames)
        upsert(upserts)
    }
}
