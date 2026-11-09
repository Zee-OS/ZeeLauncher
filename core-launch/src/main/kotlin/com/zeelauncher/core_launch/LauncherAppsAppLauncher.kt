package com.zeelauncher.core_launch

import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.LauncherApps
import android.net.Uri
import android.os.Process
import com.zeelauncher.core_launch.attr.LaunchAttribute
import com.zeelauncher.core_model.launch.LaunchResult
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal class LauncherAppsAppLauncher @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val launcherApps: LauncherApps,
) : AppLauncher {

    override fun launch(packageName: String, activityName: String): LaunchResult = runCatchingLaunch {
        launcherApps.startMainActivity(ComponentName(packageName, activityName), Process.myUserHandle(), null, null)
    }

    override fun uninstall(packageName: String): LaunchResult = runCatchingLaunch {
        val uri = Uri.fromParts(LaunchAttribute.Launcher.PACKAGE_SCHEME, packageName, null)
        val intent = Intent(Intent.ACTION_DELETE, uri)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
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
