package com.zeelauncher.core_data.datasource

import com.zeelauncher.core_database.entity.LibraryGameEntity
import kotlinx.coroutines.flow.Flow

internal interface LibraryLocalDataSource {

    fun observeGames(): Flow<List<LibraryGameEntity>>

    suspend fun getGames(): List<LibraryGameEntity>

    suspend fun syncGames(removedPackageNames: List<String>, upserts: List<LibraryGameEntity>)

    suspend fun upsertGame(game: LibraryGameEntity)

    suspend fun updateLastPlayed(packageName: String, playedAt: Long)

    suspend fun getHiddenPackageNames(): Set<String>

    suspend fun hideGame(packageName: String)

    suspend fun unhideGame(packageName: String)
}
