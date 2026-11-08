package com.zeelauncher.core_data.repository

import com.zeelauncher.core_data.datasource.InstalledAppsDataSource
import com.zeelauncher.core_data.datasource.LibraryLocalDataSource
import com.zeelauncher.core_database.entity.LibraryGameEntity
import com.zeelauncher.core_model.app.InstalledApp
import com.zeelauncher.core_model.library.LibrarySyncResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LibraryRepositoryImplTest {

    private val local = FakeLibraryLocalDataSource()
    private val installed = FakeInstalledAppsDataSource()
    private val repository = LibraryRepositoryImpl(local, installed)

    private fun TestScope.collectSyncResults(): List<LibrarySyncResult> {
        val results = mutableListOf<LibrarySyncResult>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            repository.observeSyncResults().toList(results)
        }
        return results
    }

    @Test
    fun initialSyncAddsOnlyGamesWithoutNotifying() = runTest {
        val results = collectSyncResults()
        installed.apps = listOf(game("a.game"), game("b.game"), app("c.app"))

        repository.syncLibrary()

        assertEquals(setOf("a.game", "b.game"), local.packageNames())
        assertTrue(results.isEmpty())
    }

    @Test
    fun newGameAfterInitialSyncIsNotified() = runTest {
        val results = collectSyncResults()
        installed.apps = listOf(game("a.game"))
        repository.syncLibrary()

        installed.apps = listOf(game("a.game"), game("b.game"))
        repository.syncLibrary()

        assertEquals(setOf("a.game", "b.game"), local.packageNames())
        assertEquals(listOf(LibrarySyncResult(addedCount = 1, removedCount = 0)), results)
    }

    @Test
    fun uninstalledGameIsRemovedAndNotified() = runTest {
        val results = collectSyncResults()
        installed.apps = listOf(game("a.game"), game("b.game"))
        repository.syncLibrary()

        installed.apps = listOf(game("a.game"))
        repository.syncLibrary()

        assertEquals(setOf("a.game"), local.packageNames())
        assertEquals(listOf(LibrarySyncResult(addedCount = 0, removedCount = 1)), results)
    }

    @Test
    fun syncWithoutChangesDoesNotNotify() = runTest {
        val results = collectSyncResults()
        installed.apps = listOf(game("a.game"))
        repository.syncLibrary()

        repository.syncLibrary()

        assertTrue(results.isEmpty())
    }

    @Test
    fun hiddenGameIsNotAddedBack() = runTest {
        installed.apps = listOf(game("a.game"), game("b.game"))
        repository.syncLibrary()

        repository.hideGame("b.game")
        repository.syncLibrary()

        assertEquals(setOf("a.game"), local.packageNames())
    }

    @Test
    fun importedAppIsUnhiddenAndKept() = runTest {
        installed.apps = listOf(game("a.game"), app("c.app"))
        repository.syncLibrary()
        repository.hideGame("a.game")

        repository.addGame(game("a.game"))
        repository.addGame(app("c.app"))
        repository.syncLibrary()

        assertEquals(setOf("a.game", "c.app"), local.packageNames())
    }

    @Test
    fun refreshKeepsLastPlayedAndUpdatesLabel() = runTest {
        installed.apps = listOf(game("a.game", label = "Old"))
        repository.syncLibrary()
        local.updateLastPlayed("a.game", PLAYED_AT)

        installed.apps = listOf(game("a.game", label = "New"))
        repository.syncLibrary()

        val entity = local.games.getValue("a.game")
        assertEquals("New", entity.label)
        assertEquals(PLAYED_AT, entity.lastPlayedAt)
    }

    private fun game(packageName: String, label: String = packageName) =
        InstalledApp(packageName = packageName, activityName = "$packageName.Main", label = label, isGame = true)

    private fun app(packageName: String) =
        InstalledApp(packageName = packageName, activityName = "$packageName.Main", label = packageName, isGame = false)

    private companion object {
        const val PLAYED_AT = 1_000L
    }
}

private class FakeInstalledAppsDataSource : InstalledAppsDataSource {

    var apps: List<InstalledApp> = emptyList()

    override suspend fun getInstalledApps(): List<InstalledApp> = apps

    override fun observeChanges(): Flow<Unit> = emptyFlow()

    override suspend fun getVersionName(packageName: String): String? = null
}

private class FakeLibraryLocalDataSource : LibraryLocalDataSource {

    val games = linkedMapOf<String, LibraryGameEntity>()
    private val hidden = mutableSetOf<String>()
    private val state = MutableStateFlow<List<LibraryGameEntity>>(emptyList())

    fun packageNames(): Set<String> = games.keys.toSet()

    private fun publish() {
        state.value = games.values.toList()
    }

    override fun observeGames(): Flow<List<LibraryGameEntity>> = state

    override suspend fun getGames(): List<LibraryGameEntity> = games.values.toList()

    override suspend fun syncGames(removedPackageNames: List<String>, upserts: List<LibraryGameEntity>) {
        removedPackageNames.forEach { games.remove(it) }
        upserts.forEach { games[it.packageName] = it }
        publish()
    }

    override suspend fun upsertGame(game: LibraryGameEntity) {
        games[game.packageName] = game
        publish()
    }

    override suspend fun updateLastPlayed(packageName: String, playedAt: Long) {
        games[packageName]?.let { games[packageName] = it.copy(lastPlayedAt = playedAt) }
        publish()
    }

    override suspend fun getHiddenPackageNames(): Set<String> = hidden.toSet()

    override suspend fun hideGame(packageName: String) {
        hidden.add(packageName)
        games.remove(packageName)
        publish()
    }

    override suspend fun unhideGame(packageName: String) {
        hidden.remove(packageName)
    }
}
