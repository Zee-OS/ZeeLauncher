package com.zeelauncher.core_launch

import android.app.role.RoleManager
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal class RoleManagerLauncherRole @Inject constructor(
    @param:ApplicationContext private val context: Context,
) : LauncherRole {

    override fun isDefaultLauncher(): Boolean =
        context.getSystemService(RoleManager::class.java).isRoleHeld(RoleManager.ROLE_HOME)
}
