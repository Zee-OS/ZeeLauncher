package com.zeelauncher.core_designsystem.modifier

import androidx.compose.foundation.clickable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp
import com.zeelauncher.core_common.constant.TWO_FLOAT
import com.zeelauncher.core_common.constant.ZERO
import com.zeelauncher.core_common.constant.ZERO_FLOAT
import com.zeelauncher.core_designsystem.theme.ZeeDimens
import kotlin.math.roundToInt

fun Modifier.hardShadow(offset: Dp, color: Color): Modifier = drawBehind {
    val o = offset.toPx()
    drawRect(color, topLeft = Offset(o, o), size = size)
}

fun Modifier.dashedBorder(width: Dp, color: Color): Modifier = drawWithContent {
    drawContent()
    val w = width.toPx()
    val half = w / TWO_FLOAT
    drawRect(
        color = color,
        topLeft = Offset(half, half),
        size = Size(size.width - w, size.height - w),
        style = Stroke(
            width = w,
            pathEffect = PathEffect.dashPathEffect(
                floatArrayOf(ZeeDimens.DashLength.toPx(), ZeeDimens.DashGap.toPx()),
            ),
        ),
    )
}

fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier =
    clickable(interactionSource = null, indication = null, onClick = onClick)

fun Modifier.revealHeight(fraction: Float): Modifier = clipToBounds().layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)
    val height = (placeable.height * fraction).roundToInt()
    layout(placeable.width, height) { placeable.place(ZERO, ZERO) }
}

fun Modifier.hatch(color: Color): Modifier = clipToBounds().drawBehind {
    val spacing = ZeeDimens.HatchSpacing.toPx()
    val stroke = ZeeDimens.HatchStroke.toPx()
    var startX = -size.height
    while (startX < size.width) {
        drawLine(
            color = color,
            start = Offset(startX, size.height),
            end = Offset(startX + size.height, ZERO_FLOAT),
            strokeWidth = stroke,
        )
        startX += spacing
    }
}
