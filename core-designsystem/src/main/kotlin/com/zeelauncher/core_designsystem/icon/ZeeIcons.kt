package com.zeelauncher.core_designsystem.icon

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import com.zeelauncher.core_common.constant.ONE_HUNDRED
import com.zeelauncher.core_common.constant.ONE_HUNDRED_FLOAT
import com.zeelauncher.core_common.constant.TWO_FLOAT
import com.zeelauncher.core_common.constant.ZERO
import com.zeelauncher.core_common.constant.ZERO_FLOAT
import com.zeelauncher.core_designsystem.theme.Dimen
import com.zeelauncher.core_designsystem.theme.ZeeDimens
import com.zeelauncher.core_designsystem.theme.ZeeTheme

private const val ICON_VIEWPORT = 22f

private const val BELL_LEFT = 5f
private const val BELL_RIGHT = 17f
private const val BELL_CENTER = 11f
private const val BELL_TOP = 3.5f
private const val BELL_SHOULDER = 10f
private const val BELL_CURVE_Y = 6f
private const val BELL_CURVE_LEFT = 7.5f
private const val BELL_CURVE_RIGHT = 14.5f
private const val BELL_BOTTOM = 16f
private const val BELL_BASE_LEFT = 3f
private const val BELL_BASE_RIGHT = 19f
private const val BELL_CLAPPER_LEFT = 9f
private const val BELL_CLAPPER_RIGHT = 13f
private const val BELL_CLAPPER_Y = 19.5f

private const val WIFI_CENTER_X = 11f
private const val WIFI_CENTER_Y = 19f
private const val WIFI_RADIUS_SMALL = 4.5f
private const val WIFI_RADIUS_MEDIUM = 8.5f
private const val WIFI_RADIUS_LARGE = 12.5f
private const val WIFI_START_ANGLE = 225f
private const val WIFI_SWEEP_ANGLE = 90f
private const val WIFI_DOT_RADIUS = 1.6f

private const val BATTERY_LOW_THRESHOLD = 20

@Composable
fun ZeeBellIcon(
    modifier: Modifier = Modifier,
    color: Color = ZeeTheme.colors.content,
) {
    Canvas(modifier.size(ZeeDimens.IconSize)) {
        val u = size.width / ICON_VIEWPORT
        val stroke = ZeeDimens.IconStroke.toPx()
        val body = Path().apply {
            moveTo(BELL_LEFT * u, BELL_BOTTOM * u)
            lineTo(BELL_LEFT * u, BELL_SHOULDER * u)
            cubicTo(BELL_LEFT * u, BELL_CURVE_Y * u, BELL_CURVE_LEFT * u, BELL_TOP * u, BELL_CENTER * u, BELL_TOP * u)
            cubicTo(BELL_CURVE_RIGHT * u, BELL_TOP * u, BELL_RIGHT * u, BELL_CURVE_Y * u, BELL_RIGHT * u, BELL_SHOULDER * u)
            lineTo(BELL_RIGHT * u, BELL_BOTTOM * u)
        }
        drawPath(body, color, style = Stroke(width = stroke, cap = StrokeCap.Square))
        drawLine(
            color = color,
            start = Offset(BELL_BASE_LEFT * u, BELL_BOTTOM * u),
            end = Offset(BELL_BASE_RIGHT * u, BELL_BOTTOM * u),
            strokeWidth = stroke,
            cap = StrokeCap.Square,
        )
        drawLine(
            color = color,
            start = Offset(BELL_CLAPPER_LEFT * u, BELL_CLAPPER_Y * u),
            end = Offset(BELL_CLAPPER_RIGHT * u, BELL_CLAPPER_Y * u),
            strokeWidth = stroke,
            cap = StrokeCap.Square,
        )
    }
}

@Composable
fun ZeeWifiIcon(
    modifier: Modifier = Modifier,
    color: Color = ZeeTheme.colors.content,
) {
    Canvas(modifier.size(ZeeDimens.IconSize)) {
        val u = size.width / ICON_VIEWPORT
        val center = Offset(WIFI_CENTER_X * u, WIFI_CENTER_Y * u)
        listOf(WIFI_RADIUS_SMALL, WIFI_RADIUS_MEDIUM, WIFI_RADIUS_LARGE).forEach { radius ->
            val r = radius * u
            drawArc(
                color = color,
                startAngle = WIFI_START_ANGLE,
                sweepAngle = WIFI_SWEEP_ANGLE,
                useCenter = false,
                topLeft = Offset(center.x - r, center.y - r),
                size = Size(r * TWO_FLOAT, r * TWO_FLOAT),
                style = Stroke(width = ZeeDimens.IconStroke.toPx(), cap = StrokeCap.Square),
            )
        }
        drawCircle(color, WIFI_DOT_RADIUS * u, center)
    }
}

@Composable
fun ZeeBatteryIcon(
    percent: Int,
    modifier: Modifier = Modifier,
) {
    val colors = ZeeTheme.colors
    val fill = if (percent <= BATTERY_LOW_THRESHOLD) colors.accent else colors.success
    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        Box(
            Modifier
                .size(Dimen.Dp44, Dimen.Dp22)
                .border(ZeeDimens.BorderThin, colors.content)
                .padding(Dimen.Dp5),
        ) {
            Box(
                Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(percent.coerceIn(ZERO, ONE_HUNDRED) / ONE_HUNDRED_FLOAT)
                    .background(fill),
            )
        }
        Box(
            Modifier
                .size(Dimen.Dp4, Dimen.Dp9)
                .background(colors.content),
        )
    }
}

@Composable
fun ZeePlusIcon(
    size: Dp,
    stroke: Dp,
    modifier: Modifier = Modifier,
    color: Color = ZeeTheme.colors.accent,
) {
    Canvas(modifier.size(size)) {
        val s = stroke.toPx()
        drawRect(
            color = color,
            topLeft = Offset((this.size.width - s) / TWO_FLOAT, ZERO_FLOAT),
            size = Size(s, this.size.height),
        )
        drawRect(
            color = color,
            topLeft = Offset(ZERO_FLOAT, (this.size.height - s) / TWO_FLOAT),
            size = Size(this.size.width, s),
        )
    }
}
