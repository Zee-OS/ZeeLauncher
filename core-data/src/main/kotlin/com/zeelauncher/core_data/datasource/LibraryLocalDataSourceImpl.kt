package com.zeelauncher.core_data.datasource

import com.zeelauncher.core_database.dao.HiddenGameDao
import com.zeelauncher.core_database.dao.LibraryGameDao
import com.zeelauncher.core_database.entity.HiddenGameEntity
import com.zeelauncher.core_database.entity.LibraryGameEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class LibraryLocalDataSourceImpl @Inject constructor(
    private val libraryGameDao: LibraryGameDao,
    private val hiddenGameDao: HiddenGameDao,
) : LibraryLocalDataSource {

    override fun observeGames(): Flow<List<LibraryGameEntity>> = libraryGameDao.observeAll()

    override suspend fun getGames(): List<LibraryGameEntity> = libraryGameDao.getAll()

    override suspend fun syncGames(removedPackageNames: List<String>, upserts: List<LibraryGameEntity>) {
        libraryGameDao.sync(removedPackageNames, upserts)
    }

    override suspend fun upsertGame(game: LibraryGameEntity) {
        libraryGameDao.upsert(listOf(game))
    }

    override suspend fun updateLastPlayed(packageName: String, playedAt: Long) {
        libraryGameDao.updateLastPlayed(packageName, playedAt)
    }

    override suspend fun getHiddenPackageNames(): Set<String> = hiddenGameDao.getPackageNames().toSet()

    override suspend fun hideGame(packageName: String) {
        hiddenGameDao.upsert(HiddenGameEntity(packageName))
        libraryGameDao.delete(listOf(packageName))
    }

    override suspend fun unhideGame(packageName: String) {
        hiddenGameDao.delete(packageName)
    }
}
