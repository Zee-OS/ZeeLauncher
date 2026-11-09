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
import com.zeelauncher.core_common.constant.ONE
import com.zeelauncher.core_common.constant.ONE_HUNDRED
import com.zeelauncher.core_common.constant.ONE_HUNDRED_FLOAT
import com.zeelauncher.core_common.constant.TWO_FLOAT
import com.zeelauncher.core_common.constant.ZERO
import com.zeelauncher.core_common.constant.ZERO_FLOAT
import com.zeelauncher.core_designsystem.attr.DesignSystemAttribute
import com.zeelauncher.core_designsystem.theme.Dimen
import com.zeelauncher.core_designsystem.theme.ZeeDimens
import com.zeelauncher.core_designsystem.theme.ZeeTheme

@Composable
fun ZeeWifiIcon(
    modifier: Modifier = Modifier,
    color: Color = ZeeTheme.colors.content,
    crossedOut: Boolean = false,
) {
    Canvas(modifier.size(ZeeDimens.IconSize)) {
        with(DesignSystemAttribute.Icons) {
            val u = size.width / VIEWPORT
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
            if (crossedOut) {
                drawLine(
                    color,
                    Offset(SLASH_MIN * u, SLASH_MIN * u),
                    Offset(SLASH_MAX * u, SLASH_MAX * u),
                    ZeeDimens.IconStroke.toPx(),
                    StrokeCap.Square,
                )
            }
        }
    }
}

@Composable
fun ZeeSignalIcon(
    level: Int,
    modifier: Modifier = Modifier,
) {
    val colors = ZeeTheme.colors
    Canvas(modifier.size(ZeeDimens.IconSize)) {
        with(DesignSystemAttribute.Icons) {
            val u = size.width / VIEWPORT
            repeat(SIGNAL_BAR_COUNT) { index ->
                val height = SIGNAL_BAR_STEP * (index + ONE) * u
                drawRect(
                    color = if (index < level) colors.content else colors.shadow,
                    topLeft = Offset(
                        (SIGNAL_LEFT + index * (SIGNAL_BAR_WIDTH + SIGNAL_BAR_GAP)) * u,
                        SIGNAL_BOTTOM * u - height,
                    ),
                    size = Size(SIGNAL_BAR_WIDTH * u, height),
                )
            }
        }
    }
}

@Composable
fun ZeeBatteryIcon(
    percent: Int,
    modifier: Modifier = Modifier,
) {
    val colors = ZeeTheme.colors
    val fill = if (percent <= DesignSystemAttribute.Icons.BATTERY_LOW_THRESHOLD) colors.accent else colors.success
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

@Composable
fun ZeeCheckIcon(
    modifier: Modifier = Modifier,
    color: Color = ZeeTheme.colors.content,
) {
    Canvas(modifier.size(ZeeDimens.IconSize)) {
        with(DesignSystemAttribute.Icons) {
            val u = size.width / VIEWPORT
            val path = Path().apply {
                moveTo(CHECK_START_X * u, CHECK_START_Y * u)
                lineTo(CHECK_MIDDLE_X * u, CHECK_MIDDLE_Y * u)
                lineTo(CHECK_END_X * u, CHECK_END_Y * u)
            }
            drawPath(path, color, style = Stroke(width = ZeeDimens.IconStroke.toPx(), cap = StrokeCap.Square))
        }
    }
}

@Composable
fun ZeeCloseIcon(
    modifier: Modifier = Modifier,
    color: Color = ZeeTheme.colors.content,
) {
    Canvas(modifier.size(ZeeDimens.IconSize)) {
        with(DesignSystemAttribute.Icons) {
            val u = size.width / VIEWPORT
            val stroke = ZeeDimens.IconStroke.toPx()
            drawLine(
                color,
                Offset(CLOSE_MIN * u, CLOSE_MIN * u),
                Offset(CLOSE_MAX * u, CLOSE_MAX * u),
                stroke,
                StrokeCap.Square,
            )
            drawLine(
                color,
                Offset(CLOSE_MAX * u, CLOSE_MIN * u),
                Offset(CLOSE_MIN * u, CLOSE_MAX * u),
                stroke,
                StrokeCap.Square,
            )
        }
    }
}

@Composable
fun ZeeControllerIcon(
    modifier: Modifier = Modifier,
    color: Color = ZeeTheme.colors.content,
    crossedOut: Boolean = false,
) {
    Canvas(modifier.size(ZeeDimens.IconSize)) {
        with(DesignSystemAttribute.Icons) {
            val u = size.width / VIEWPORT
            val stroke = ZeeDimens.IconStroke.toPx()
            drawRect(
                color = color,
                topLeft = Offset(CONTROLLER_LEFT * u, CONTROLLER_TOP * u),
                size = Size(
                    (CONTROLLER_RIGHT - CONTROLLER_LEFT) * u,
                    (CONTROLLER_BOTTOM - CONTROLLER_TOP) * u,
                ),
                style = Stroke(width = stroke),
            )
            drawLine(
                color,
                Offset((CONTROLLER_DPAD_X - CONTROLLER_DPAD_ARM) * u, CONTROLLER_DPAD_Y * u),
                Offset((CONTROLLER_DPAD_X + CONTROLLER_DPAD_ARM) * u, CONTROLLER_DPAD_Y * u),
                stroke,
                StrokeCap.Square,
            )
            drawLine(
                color,
                Offset(CONTROLLER_DPAD_X * u, (CONTROLLER_DPAD_Y - CONTROLLER_DPAD_ARM) * u),
                Offset(CONTROLLER_DPAD_X * u, (CONTROLLER_DPAD_Y + CONTROLLER_DPAD_ARM) * u),
                stroke,
                StrokeCap.Square,
            )
            drawCircle(
                color,
                CONTROLLER_BUTTON_RADIUS * u,
                Offset(CONTROLLER_BUTTON_A_X * u, CONTROLLER_BUTTON_A_Y * u),
            )
            drawCircle(
                color,
                CONTROLLER_BUTTON_RADIUS * u,
                Offset(CONTROLLER_BUTTON_B_X * u, CONTROLLER_BUTTON_B_Y * u),
            )
            if (crossedOut) {
                drawLine(
                    color,
                    Offset(SLASH_MIN * u, SLASH_MIN * u),
                    Offset(SLASH_MAX * u, SLASH_MAX * u),
                    stroke,
                    StrokeCap.Square,
                )
            }
        }
    }
}
