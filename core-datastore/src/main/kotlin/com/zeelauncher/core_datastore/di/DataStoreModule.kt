package com.zeelauncher.core_datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.zeelauncher.core_datastore.DataStoreZeePreferences
import com.zeelauncher.core_datastore.ZeePreferences
import com.zeelauncher.core_datastore.attr.DataStoreAttribute
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataStoreModule {

    @Binds
    abstract fun bindsZeePreferences(impl: DataStoreZeePreferences): ZeePreferences

    companion object {

        @Provides
        @Singleton
        fun providesPreferencesDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
            PreferenceDataStoreFactory.create {
                context.preferencesDataStoreFile(DataStoreAttribute.Preferences.FILE_NAME)
            }
    }
}
