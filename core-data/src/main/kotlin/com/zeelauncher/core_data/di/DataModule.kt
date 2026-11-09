package com.zeelauncher.core_data.di

import com.zeelauncher.core_data.datasource.InstalledAppsDataSource
import com.zeelauncher.core_data.datasource.InstalledAppsDataSourceImpl
import com.zeelauncher.core_data.datasource.LibraryLocalDataSource
import com.zeelauncher.core_data.datasource.LibraryLocalDataSourceImpl
import com.zeelauncher.core_data.datasource.SettingsLocalDataSource
import com.zeelauncher.core_data.datasource.SettingsLocalDataSourceImpl
import com.zeelauncher.core_data.repository.InstalledAppsRepository
import com.zeelauncher.core_data.repository.InstalledAppsRepositoryImpl
import com.zeelauncher.core_data.repository.LibraryRepository
import com.zeelauncher.core_data.repository.LibraryRepositoryImpl
import com.zeelauncher.core_data.repository.SettingsRepository
import com.zeelauncher.core_data.repository.SettingsRepositoryImpl
import com.zeelauncher.core_data.usecase.AddGameToLibraryUseCase
import com.zeelauncher.core_data.usecase.AddGameToLibraryUseCaseImpl
import com.zeelauncher.core_data.usecase.GetAppLanguageUseCase
import com.zeelauncher.core_data.usecase.GetAppLanguageUseCaseImpl
import com.zeelauncher.core_data.usecase.GetAppVersionUseCase
import com.zeelauncher.core_data.usecase.GetAppVersionUseCaseImpl
import com.zeelauncher.core_data.usecase.GetImportableAppsUseCase
import com.zeelauncher.core_data.usecase.GetImportableAppsUseCaseImpl
import com.zeelauncher.core_data.usecase.GetLauncherVersionUseCase
import com.zeelauncher.core_data.usecase.GetLauncherVersionUseCaseImpl
import com.zeelauncher.core_data.usecase.IsDefaultLauncherUseCase
import com.zeelauncher.core_data.usecase.IsDefaultLauncherUseCaseImpl
import com.zeelauncher.core_data.usecase.LaunchAppUseCase
import com.zeelauncher.core_data.usecase.LaunchAppUseCaseImpl
import com.zeelauncher.core_data.usecase.LaunchGameUseCase
import com.zeelauncher.core_data.usecase.LaunchGameUseCaseImpl
import com.zeelauncher.core_data.usecase.ObserveInstalledAppsUseCase
import com.zeelauncher.core_data.usecase.ObserveInstalledAppsUseCaseImpl
import com.zeelauncher.core_data.usecase.ObserveLibrarySyncResultsUseCase
import com.zeelauncher.core_data.usecase.ObserveLibrarySyncResultsUseCaseImpl
import com.zeelauncher.core_data.usecase.ObserveLibraryUseCase
import com.zeelauncher.core_data.usecase.ObserveLibraryUseCaseImpl
import com.zeelauncher.core_data.usecase.ObserveSoundEnabledUseCase
import com.zeelauncher.core_data.usecase.ObserveSoundEnabledUseCaseImpl
import com.zeelauncher.core_data.usecase.RemoveGameFromLibraryUseCase
import com.zeelauncher.core_data.usecase.RemoveGameFromLibraryUseCaseImpl
import com.zeelauncher.core_data.usecase.SetAppLanguageUseCase
import com.zeelauncher.core_data.usecase.SetAppLanguageUseCaseImpl
import com.zeelauncher.core_data.usecase.SetSoundEnabledUseCase
import com.zeelauncher.core_data.usecase.SetSoundEnabledUseCaseImpl
import com.zeelauncher.core_data.usecase.SyncLibraryUseCase
import com.zeelauncher.core_data.usecase.SyncLibraryUseCaseImpl
import com.zeelauncher.core_data.usecase.UninstallAppUseCase
import com.zeelauncher.core_data.usecase.UninstallAppUseCaseImpl
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
    abstract fun bindsGetImportableAppsUseCase(impl: GetImportableAppsUseCaseImpl): GetImportableAppsUseCase

    @Binds
    abstract fun bindsAddGameToLibraryUseCase(impl: AddGameToLibraryUseCaseImpl): AddGameToLibraryUseCase

    @Binds
    abstract fun bindsRemoveGameFromLibraryUseCase(
        impl: RemoveGameFromLibraryUseCaseImpl,
    ): RemoveGameFromLibraryUseCase

    @Binds
    abstract fun bindsGetAppVersionUseCase(impl: GetAppVersionUseCaseImpl): GetAppVersionUseCase

    @Binds
    abstract fun bindsUninstallAppUseCase(impl: UninstallAppUseCaseImpl): UninstallAppUseCase

    @Binds
    abstract fun bindsObserveInstalledAppsUseCase(impl: ObserveInstalledAppsUseCaseImpl): ObserveInstalledAppsUseCase

    @Binds
    abstract fun bindsLaunchAppUseCase(impl: LaunchAppUseCaseImpl): LaunchAppUseCase

    @Binds
    abstract fun bindsSettingsLocalDataSource(impl: SettingsLocalDataSourceImpl): SettingsLocalDataSource

    @Binds
    abstract fun bindsSettingsRepository(impl: SettingsRepositoryImpl): SettingsRepository

    @Binds
    abstract fun bindsGetAppLanguageUseCase(impl: GetAppLanguageUseCaseImpl): GetAppLanguageUseCase

    @Binds
    abstract fun bindsGetLauncherVersionUseCase(impl: GetLauncherVersionUseCaseImpl): GetLauncherVersionUseCase

    @Binds
    abstract fun bindsIsDefaultLauncherUseCase(impl: IsDefaultLauncherUseCaseImpl): IsDefaultLauncherUseCase

    @Binds
    abstract fun bindsObserveSoundEnabledUseCase(impl: ObserveSoundEnabledUseCaseImpl): ObserveSoundEnabledUseCase

    @Binds
    abstract fun bindsSetAppLanguageUseCase(impl: SetAppLanguageUseCaseImpl): SetAppLanguageUseCase

    @Binds
    abstract fun bindsSetSoundEnabledUseCase(impl: SetSoundEnabledUseCaseImpl): SetSoundEnabledUseCase
}
