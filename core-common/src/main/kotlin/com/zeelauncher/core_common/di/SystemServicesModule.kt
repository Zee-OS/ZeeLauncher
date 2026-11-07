package com.zeelauncher.core_common.di

import android.content.Context
import android.content.pm.LauncherApps
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SystemServicesModule {

    @Provides
    @Singleton
    fun providesLauncherApps(@ApplicationContext context: Context): LauncherApps =
        requireNotNull(context.getSystemService(LauncherApps::class.java))
}
