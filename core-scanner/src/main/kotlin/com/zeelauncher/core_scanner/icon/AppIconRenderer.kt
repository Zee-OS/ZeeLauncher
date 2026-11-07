package com.zeelauncher.core_scanner.icon

import android.content.ComponentName
import android.content.Intent
import android.content.pm.LauncherApps
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.AdaptiveIconDrawable
import android.graphics.drawable.Drawable
import android.os.Process
import android.util.DisplayMetrics
import com.zeelauncher.core_common.constant.MINUS_ONE
import com.zeelauncher.core_common.constant.ONE
import com.zeelauncher.core_common.constant.TWO
import com.zeelauncher.core_common.constant.ZERO
import com.zeelauncher.core_model.app.AppIcon
import javax.inject.Inject
import kotlin.math.max

private const val ICON_SIZE_PX = 512
private const val ALPHA_SHIFT = 24
private const val ALPHA_VISIBLE_THRESHOLD = 16

internal class AppIconRenderer @Inject constructor(
    private val launcherApps: LauncherApps,
) {

    fun render(icon: AppIcon): Bitmap? {
        val intent = Intent().setComponent(ComponentName(icon.packageName, icon.activityName))
        val info = launcherApps.resolveActivity(intent, Process.myUserHandle()) ?: return null
        return info.getIcon(DisplayMetrics.DENSITY_XXXHIGH).toSquareBitmap().trimTransparentEdges()
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

    private fun Bitmap.trimTransparentEdges(): Bitmap {
        val bounds = visibleBounds() ?: return this
        if (bounds.width() == width && bounds.height() == height) return this
        val side = max(bounds.width(), bounds.height())
        val left = (bounds.centerX() - side / TWO).coerceIn(ZERO, width - side)
        val top = (bounds.centerY() - side / TWO).coerceIn(ZERO, height - side)
        val cropped = Bitmap.createBitmap(this, left, top, side, side)
        return Bitmap.createScaledBitmap(cropped, ICON_SIZE_PX, ICON_SIZE_PX, true)
    }

    private fun Bitmap.visibleBounds(): Rect? {
        val pixels = IntArray(width * height)
        getPixels(pixels, ZERO, width, ZERO, ZERO, width, height)
        var minX = width
        var minY = height
        var maxX = MINUS_ONE
        var maxY = MINUS_ONE
        for (y in ZERO until height) {
            for (x in ZERO until width) {
                if (pixels[y * width + x] ushr ALPHA_SHIFT > ALPHA_VISIBLE_THRESHOLD) {
                    if (x < minX) minX = x
                    if (x > maxX) maxX = x
                    if (y < minY) minY = y
                    if (y > maxY) maxY = y
                }
            }
        }
        return if (maxX == MINUS_ONE) null else Rect(minX, minY, maxX + ONE, maxY + ONE)
    }
}
