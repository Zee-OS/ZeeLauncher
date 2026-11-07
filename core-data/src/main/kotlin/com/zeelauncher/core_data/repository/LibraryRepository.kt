package com.zeelauncher.core_data.repository

import com.zeelauncher.core_model.app.InstalledApp
import com.zeelauncher.core_model.game.Game
import com.zeelauncher.core_model.library.LibrarySyncResult
import kotlinx.coroutines.flow.Flow

interface LibraryRepository {

    fun observeLibrary(): Flow<List<Game>>

    fun observeSyncResults(): Flow<LibrarySyncResult>

    suspend fun syncLibrary()

    suspend fun markPlayed(packageName: String)

    suspend fun addGame(app: InstalledApp)

    suspend fun hideGame(packageName: String)
}
