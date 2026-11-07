package com.zeelauncher.core_scanner.di

import android.content.Context
import coil3.ImageLoader
import com.zeelauncher.core_scanner.app.InstalledAppsSource
import com.zeelauncher.core_scanner.app.LauncherAppsInstalledAppsSource
import com.zeelauncher.core_scanner.icon.AppIconFetcher
import com.zeelauncher.core_scanner.icon.AppIconKeyer
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ScannerModule {

    @Binds
    @Singleton
    abstract fun bindsInstalledAppsSource(impl: LauncherAppsInstalledAppsSource): InstalledAppsSource

    companion object {

        @Provides
        @Singleton
        fun providesImageLoader(
            @ApplicationContext context: Context,
            appIconFetcherFactory: AppIconFetcher.Factory,
            appIconKeyer: AppIconKeyer,
        ): ImageLoader = ImageLoader.Builder(context)
            .components {
                add(appIconFetcherFactory)
                add(appIconKeyer)
            }
            .build()
    }
}
