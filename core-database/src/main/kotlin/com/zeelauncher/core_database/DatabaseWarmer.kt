package com.zeelauncher.core_database

import com.zeelauncher.core_database.dao.LibraryGameDao
import javax.inject.Inject

class DatabaseWarmer @Inject constructor(
    private val libraryGameDao: LibraryGameDao,
) {

    suspend fun warmUp() {
        libraryGameDao.getAll()
    }
}