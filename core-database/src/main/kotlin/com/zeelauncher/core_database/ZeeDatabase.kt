package com.zeelauncher.core_database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zeelauncher.core_database.dao.HiddenGameDao
import com.zeelauncher.core_database.dao.LibraryGameDao
import com.zeelauncher.core_database.entity.HiddenGameEntity
import com.zeelauncher.core_database.entity.LibraryGameEntity

internal const val DATABASE_NAME = "zee_launcher.db"
private const val DATABASE_VERSION = 1

@Database(
    entities = [
        LibraryGameEntity::class,
        HiddenGameEntity::class,
    ],
    version = DATABASE_VERSION,
    exportSchema = true,
)
internal abstract class ZeeDatabase : RoomDatabase() {

    abstract fun libraryGameDao(): LibraryGameDao

    abstract fun hiddenGameDao(): HiddenGameDao
}
