package com.zeelauncher.core_data.di

import com.zeelauncher.core_data.datasource.InstalledAppsDataSource
import com.zeelauncher.core_data.datasource.InstalledAppsDataSourceImpl
import com.zeelauncher.core_data.datasource.LibraryLocalDataSource
import com.zeelauncher.core_data.datasource.LibraryLocalDataSourceImpl
import com.zeelauncher.core_data.repository.InstalledAppsRepository
import com.zeelauncher.core_data.repository.InstalledAppsRepositoryImpl
import com.zeelauncher.core_data.repository.LibraryRepository
import com.zeelauncher.core_data.repository.LibraryRepositoryImpl
import com.zeelauncher.core_data.usecase.LaunchGameUseCase
import com.zeelauncher.core_data.usecase.LaunchGameUseCaseImpl
import com.zeelauncher.core_data.usecase.ObserveLibrarySyncResultsUseCase
import com.zeelauncher.core_data.usecase.ObserveLibrarySyncResultsUseCaseImpl
import com.zeelauncher.core_data.usecase.ObserveLibraryUseCase
import com.zeelauncher.core_data.usecase.ObserveLibraryUseCaseImpl
import com.zeelauncher.core_data.usecase.OpenAppInfoUseCase
import com.zeelauncher.core_data.usecase.OpenAppInfoUseCaseImpl
import com.zeelauncher.core_data.usecase.SyncLibraryUseCase
import com.zeelauncher.core_data.usecase.SyncLibraryUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataModule {

    @Binds
    abstract fun bindsLibraryLocalDataSource(impl: LibraryLocalDataSourceImpl): LibraryLocalDataSource

    @Binds
    abstract fun bindsInstalledAppsDataSource(impl: InstalledAppsDataSourceImpl): InstalledAppsDataSource

    @Binds
    abstract fun bindsLibraryRepository(impl: LibraryRepositoryImpl): LibraryRepository

    @Binds
    abstract fun bindsInstalledAppsRepository(impl: InstalledAppsRepositoryImpl): InstalledAppsRepository

    @Binds
    abstract fun bindsObserveLibraryUseCase(impl: ObserveLibraryUseCaseImpl): ObserveLibraryUseCase

    @Binds
    abstract fun bindsObserveLibrarySyncResultsUseCase(
        impl: ObserveLibrarySyncResultsUseCaseImpl,
    ): ObserveLibrarySyncResultsUseCase

    @Binds
    abstract fun bindsSyncLibraryUseCase(impl: SyncLibraryUseCaseImpl): SyncLibraryUseCase

    @Binds
    abstract fun bindsLaunchGameUseCase(impl: LaunchGameUseCaseImpl): LaunchGameUseCase

    @Binds
    abstract fun bindsOpenAppInfoUseCase(impl: OpenAppInfoUseCaseImpl): OpenAppInfoUseCase
}
