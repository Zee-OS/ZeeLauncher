package com.zeelauncher.core_launch.di

import com.zeelauncher.core_launch.AppLauncher
import com.zeelauncher.core_launch.LauncherAppsAppLauncher
import com.zeelauncher.core_launch.LauncherRole
import com.zeelauncher.core_launch.RoleManagerLauncherRole
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class LaunchModule {

    @Binds
    abstract fun bindsAppLauncher(impl: LauncherAppsAppLauncher): AppLauncher

    @Binds
    abstract fun bindsLauncherRole(impl: RoleManagerLauncherRole): LauncherRole
}
