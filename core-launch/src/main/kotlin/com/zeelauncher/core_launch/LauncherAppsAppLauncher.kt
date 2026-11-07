package com.zeelauncher.core_launch

import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.pm.LauncherApps
import android.os.Process
import com.zeelauncher.core_model.launch.LaunchResult
import javax.inject.Inject

internal class LauncherAppsAppLauncher @Inject constructor(
    private val launcherApps: LauncherApps,
) : AppLauncher {

    override fun launch(packageName: String, activityName: String): LaunchResult = runCatchingLaunch {
        launcherApps.startMainActivity(ComponentName(packageName, activityName), Process.myUserHandle(), null, null)
    }

    override fun openAppInfo(packageName: String, activityName: String): LaunchResult = runCatchingLaunch {
        launcherApps.startAppDetailsActivity(ComponentName(packageName, activityName), Process.myUserHandle(), null, null)
    }

    private inline fun runCatchingLaunch(block: () -> Unit): LaunchResult = try {
        block()
        LaunchResult.Success
    } catch (exception: ActivityNotFoundException) {
        LaunchResult.AppNotFound
    } catch (exception: SecurityException) {
        LaunchResult.AppNotFound
    } catch (exception: IllegalStateException) {
        LaunchResult.AppNotFound
    }
}
