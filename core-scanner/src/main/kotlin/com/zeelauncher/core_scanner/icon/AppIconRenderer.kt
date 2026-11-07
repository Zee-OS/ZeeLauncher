package com.zeelauncher.core_scanner.icon

import android.content.ComponentName
import android.content.Intent
import android.content.pm.LauncherApps
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.AdaptiveIconDrawable
import android.graphics.drawable.Drawable
import android.os.Process
import android.util.DisplayMetrics
import com.zeelauncher.core_common.constant.ZERO
import com.zeelauncher.core_model.app.AppIcon
import javax.inject.Inject

private const val ICON_SIZE_PX = 512

internal class AppIconRenderer @Inject constructor(
    private val launcherApps: LauncherApps,
) {

    fun render(icon: AppIcon): Bitmap? {
        val intent = Intent().setComponent(ComponentName(icon.packageName, icon.activityName))
        val info = launcherApps.resolveActivity(intent, Process.myUserHandle()) ?: return null
        return info.getIcon(DisplayMetrics.DENSITY_XXXHIGH).toSquareBitmap()
    }

    private fun Drawable.toSquareBitmap(): Bitmap {
        val bitmap = Bitmap.createBitmap(ICON_SIZE_PX, ICON_SIZE_PX, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val layers = if (this is AdaptiveIconDrawable) listOfNotNull(background, foreground) else listOf(this)
        layers.forEach { layer ->
            layer.setBounds(ZERO, ZERO, ICON_SIZE_PX, ICON_SIZE_PX)
            layer.draw(canvas)
        }
        return bitmap
    }
}
