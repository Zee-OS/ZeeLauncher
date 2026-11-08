package com.zeelauncher.core_scanner.app

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.LauncherActivityInfo
import android.content.pm.LauncherApps
import android.content.pm.PackageManager
import android.os.Handler
import android.os.Looper
import android.os.Process
import android.os.UserHandle
import com.zeelauncher.core_common.constant.ZERO
import com.zeelauncher.core_common.dispatcher.IoDispatcher
import com.zeelauncher.core_model.app.AppCategory
import com.zeelauncher.core_model.app.InstalledApp
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class LauncherAppsInstalledAppsSource @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val launcherApps: LauncherApps,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : InstalledAppsSource {

    override suspend fun getInstalledApps(): List<InstalledApp> = withContext(ioDispatcher) {
        launcherApps.getActivityList(null, Process.myUserHandle())
            .filter { it.applicationInfo.packageName != context.packageName }
            .distinctBy { it.applicationInfo.packageName }
            .map { it.toInstalledApp() }
    }

    override fun observeChanges(): Flow<Unit> = callbackFlow {
        val callback = object : LauncherApps.Callback() {
            override fun onPackageAdded(packageName: String, user: UserHandle) {
                trySend(Unit)
            }

            override fun onPackageRemoved(packageName: String, user: UserHandle) {
                trySend(Unit)
            }

            override fun onPackageChanged(packageName: String, user: UserHandle) {
                trySend(Unit)
            }

            override fun onPackagesAvailable(packageNames: Array<String>, user: UserHandle, replacing: Boolean) {
                trySend(Unit)
            }

            override fun onPackagesUnavailable(packageNames: Array<String>, user: UserHandle, replacing: Boolean) {
                trySend(Unit)
            }
        }
        launcherApps.registerCallback(callback, Handler(Looper.getMainLooper()))
        awaitClose { launcherApps.unregisterCallback(callback) }
    }

    override suspend fun getVersionName(packageName: String): String? = withContext(ioDispatcher) {
        try {
            context.packageManager.getPackageInfo(packageName, ZERO).versionName
        } catch (exception: PackageManager.NameNotFoundException) {
            null
        }
    }

    private fun LauncherActivityInfo.toInstalledApp(): InstalledApp {
        val isGame = applicationInfo.isGame()
        return InstalledApp(
            packageName = applicationInfo.packageName,
            activityName = componentName.className,
            label = label.toString(),
            isGame = isGame,
            category = if (isGame) AppCategory.Game else applicationInfo.toAppCategory(),
        )
    }

    private fun ApplicationInfo.toAppCategory(): AppCategory = when (category) {
        ApplicationInfo.CATEGORY_GAME -> AppCategory.Game
        ApplicationInfo.CATEGORY_AUDIO -> AppCategory.Audio
        ApplicationInfo.CATEGORY_VIDEO -> AppCategory.Video
        ApplicationInfo.CATEGORY_IMAGE -> AppCategory.Image
        ApplicationInfo.CATEGORY_SOCIAL -> AppCategory.Social
        ApplicationInfo.CATEGORY_NEWS -> AppCategory.News
        ApplicationInfo.CATEGORY_MAPS -> AppCategory.Maps
        ApplicationInfo.CATEGORY_PRODUCTIVITY -> AppCategory.Productivity
        else -> AppCategory.Undefined
    }

    @Suppress("DEPRECATION")
    private fun ApplicationInfo.isGame(): Boolean =
        category == ApplicationInfo.CATEGORY_GAME || (flags and ApplicationInfo.FLAG_IS_GAME) != ZERO
}
