package com.zeelauncher.core_database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.zeelauncher.core_database.entity.LIBRARY_GAME_LABEL
import com.zeelauncher.core_database.entity.LIBRARY_GAME_LAST_PLAYED_AT
import com.zeelauncher.core_database.entity.LIBRARY_GAME_PACKAGE_NAME
import com.zeelauncher.core_database.entity.LIBRARY_GAME_TABLE
import com.zeelauncher.core_database.entity.LibraryGameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LibraryGameDao {

    @Query("SELECT * FROM $LIBRARY_GAME_TABLE ORDER BY $LIBRARY_GAME_LABEL COLLATE NOCASE ASC")
    fun observeAll(): Flow<List<LibraryGameEntity>>

    @Query("SELECT * FROM $LIBRARY_GAME_TABLE")
    suspend fun getAll(): List<LibraryGameEntity>

    @Upsert
    suspend fun upsert(entities: List<LibraryGameEntity>)

    @Query("DELETE FROM $LIBRARY_GAME_TABLE WHERE $LIBRARY_GAME_PACKAGE_NAME IN (:packageNames)")
    suspend fun delete(packageNames: List<String>)

    @Query(
        "UPDATE $LIBRARY_GAME_TABLE SET $LIBRARY_GAME_LAST_PLAYED_AT = :playedAt " +
            "WHERE $LIBRARY_GAME_PACKAGE_NAME = :packageName",
    )
    suspend fun updateLastPlayed(packageName: String, playedAt: Long)

    @Transaction
    suspend fun sync(removedPackageNames: List<String>, upserts: List<LibraryGameEntity>) {
        delete(removedPackageNames)
        upsert(upserts)
    }
}
