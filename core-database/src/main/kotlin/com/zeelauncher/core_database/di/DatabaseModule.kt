package com.zeelauncher.core_database.di

import android.content.Context
import androidx.room.Room
import com.zeelauncher.core_database.DATABASE_NAME
import com.zeelauncher.core_database.ZeeDatabase
import com.zeelauncher.core_database.dao.HiddenGameDao
import com.zeelauncher.core_database.dao.LibraryGameDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun providesZeeDatabase(@ApplicationContext context: Context): ZeeDatabase =
        Room.databaseBuilder(context, ZeeDatabase::class.java, DATABASE_NAME).build()

    @Provides
    fun providesLibraryGameDao(database: ZeeDatabase): LibraryGameDao = database.libraryGameDao()

    @Provides
    fun providesHiddenGameDao(database: ZeeDatabase): HiddenGameDao = database.hiddenGameDao()
}
