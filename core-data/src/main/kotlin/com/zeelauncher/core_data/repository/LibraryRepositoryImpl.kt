package com.zeelauncher.core_data.repository

import com.zeelauncher.core_data.datasource.InstalledAppsDataSource
import com.zeelauncher.core_data.datasource.LibraryLocalDataSource
import com.zeelauncher.core_data.mapper.toGame
import com.zeelauncher.core_data.mapper.toLibraryGameEntity
import com.zeelauncher.core_model.app.InstalledApp
import com.zeelauncher.core_model.game.Game
import com.zeelauncher.core_model.library.LibrarySyncResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject
import javax.inject.Singleton

private const val SYNC_RESULT_BUFFER_CAPACITY = 4

@Singleton
internal class LibraryRepositoryImpl @Inject constructor(
    private val libraryLocalDataSource: LibraryLocalDataSource,
    private val installedAppsDataSource: InstalledAppsDataSource,
) : LibraryRepository {

    private val syncMutex = Mutex()
    private var hasSyncedOnce = false
    private val syncResults = MutableSharedFlow<LibrarySyncResult>(extraBufferCapacity = SYNC_RESULT_BUFFER_CAPACITY)

    override fun observeLibrary(): Flow<List<Game>> =
        libraryLocalDataSource.observeGames().map { games -> games.map { it.toGame() } }

    override fun observeSyncResults(): Flow<LibrarySyncResult> = syncResults.asSharedFlow()

    override suspend fun syncLibrary() {
        syncMutex.withLock { syncLocked() }
    }

    private suspend fun syncLocked() {
        val installed = installedAppsDataSource.getInstalledApps().associateBy { it.packageName }
        val hidden = libraryLocalDataSource.getHiddenPackageNames()
        val current = libraryLocalDataSource.getGames()
        val currentPackageNames = current.map { it.packageName }.toSet()

        val removed = current
            .filter { it.packageName !in installed }
            .map { it.packageName }
        val refreshed = current.mapNotNull { game ->
            installed[game.packageName]?.toLibraryGameEntity(lastPlayedAt = game.lastPlayedAt)
        }
        val added = installed.values
            .filter { it.isGame && it.packageName !in hidden && it.packageName !in currentPackageNames }
            .map { it.toLibraryGameEntity() }

        libraryLocalDataSource.syncGames(removed, refreshed + added)

        val isInitialPopulation = !hasSyncedOnce && current.isEmpty()
        hasSyncedOnce = true

        val result = LibrarySyncResult(addedCount = added.size, removedCount = removed.size)
        if (result.hasChanges && !isInitialPopulation) syncResults.emit(result)
    }

    override suspend fun markPlayed(packageName: String) {
        libraryLocalDataSource.updateLastPlayed(packageName, System.currentTimeMillis())
    }

    override suspend fun addGame(app: InstalledApp) {
        libraryLocalDataSource.unhideGame(app.packageName)
        libraryLocalDataSource.upsertGame(app.toLibraryGameEntity())
    }

    override suspend fun hideGame(packageName: String) {
        libraryLocalDataSource.hideGame(packageName)
    }
}
