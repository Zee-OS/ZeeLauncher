package com.zeelauncher.core_data.repository

import com.zeelauncher.core_model.app.InstalledApp
import com.zeelauncher.core_model.game.Game
import kotlinx.coroutines.flow.Flow

interface LibraryRepository {

    fun observeLibrary(): Flow<List<Game>>

    suspend fun syncLibrary()

    suspend fun markPlayed(packageName: String)

    suspend fun addGame(app: InstalledApp)

    suspend fun hideGame(packageName: String)
}
