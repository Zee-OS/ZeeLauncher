package com.zeelauncher.core_launch

import com.zeelauncher.core_model.launch.LaunchResult

interface AppLauncher {

    fun launch(packageName: String, activityName: String): LaunchResult

    fun openAppInfo(packageName: String, activityName: String): LaunchResult
}
