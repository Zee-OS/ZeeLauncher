package com.zeelauncher.core_designsystem.toast

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.graphicsLayer
import com.zeelauncher.core_common.constant.ONE
import com.zeelauncher.core_common.constant.ONE_FLOAT
import com.zeelauncher.core_common.constant.ZERO_FLOAT
import com.zeelauncher.core_common.constant.ZERO_LONG
import com.zeelauncher.core_designsystem.attr.DesignSystemAttribute
import com.zeelauncher.core_designsystem.icon.ZeeCheckIcon
import com.zeelauncher.core_designsystem.icon.ZeeCloseIcon
import com.zeelauncher.core_designsystem.icon.ZeeControllerIcon
import com.zeelauncher.core_designsystem.modifier.hardShadow
import com.zeelauncher.core_designsystem.modifier.revealHeight
import com.zeelauncher.core_designsystem.motion.ZeeMotion
import com.zeelauncher.core_designsystem.theme.Dimen
import com.zeelauncher.core_designsystem.theme.ZeeDimens
import com.zeelauncher.core_designsystem.theme.ZeeTheme
import kotlinx.coroutines.delay

enum class ZeeToastIcon {
    Check,
    Close,
    Controller,
    ControllerOff,
}

internal class ZeeToastItem(
    val id: Long,
    val text: String,
    val icon: ZeeToastIcon,
) {
    var isDismissed by mutableStateOf(false)
        private set

    fun dismiss() {
        isDismissed = true
    }
}

@Stable
class ZeeToastHostState {

    internal val items = mutableStateListOf<ZeeToastItem>()
    private var nextId = ZERO_LONG

    fun show(text: String, icon: ZeeToastIcon) {
        val active = items.filterNot { it.isDismissed }
        if (active.size >= DesignSystemAttribute.Toast.MAX_VISIBLE) active.first().dismiss()
        items.add(ZeeToastItem(id = nextId++, text = text, icon = icon))
    }

    internal fun remove(item: ZeeToastItem) {
        items.remove(item)
    }
}

val LocalZeeToast = staticCompositionLocalOf { ZeeToastHostState() }

@Composable
fun ZeeToastHost(
    state: ZeeToastHostState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier.padding(start = ZeeDimens.ScreenPaddingHorizontal, top = ZeeDimens.ToastTop),
    ) {
        state.items.forEach { item ->
            key(item.id) {
                val slide = remember { Animatable(ZERO_FLOAT) }
                val height = remember { Animatable(ONE_FLOAT) }

                LaunchedEffect(item) {
                    slide.animateTo(ONE_FLOAT, ZeeMotion.spec())
                    delay(DesignSystemAttribute.Toast.DURATION_MILLIS)
                    item.dismiss()
                }

                LaunchedEffect(item.isDismissed) {
                    if (item.isDismissed) {
                        slide.animateTo(ZERO_FLOAT, ZeeMotion.spec())
                        height.animateTo(ZERO_FLOAT, ZeeMotion.spec())
                        state.remove(item)
                    }
                }

                ZeeToastView(
                    item = item,
                    modifier = Modifier
                        .graphicsLayer {
                            val hiddenOffset = size.width + ZeeDimens.ScreenPaddingHorizontal.toPx()
                            translationX = -hiddenOffset * (ONE_FLOAT - slide.value)
                        }
                        .revealHeight(height.value)
                        .padding(end = ZeeDimens.ShadowMedium, bottom = ZeeDimens.ToastGap),
                )
            }
        }
    }
}

@Composable
private fun ZeeToastView(
    item: ZeeToastItem,
    modifier: Modifier = Modifier,
) {
    val colors = ZeeTheme.colors
    Box(modifier) {
        Row(
            modifier = Modifier
                .height(ZeeDimens.ToastHeight)
                .hardShadow(ZeeDimens.ShadowMedium, colors.shadow)
                .background(colors.background)
                .border(ZeeDimens.BorderThin, colors.content),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(ZeeDimens.ToastHeight)
                    .background(colors.accent)
                    .drawBehind {
                        val border = ZeeDimens.BorderThin.toPx()
                        drawRect(
                            color = colors.content,
                            topLeft = Offset(size.width - border, ZERO_FLOAT),
                            size = Size(border, size.height),
                        )
                    },
                contentAlignment = Alignment.Center,
            ) {
                ToastIcon(item.icon)
            }
            Text(
                text = item.text,
                style = ZeeTheme.typography.button,
                color = colors.content,
                maxLines = ONE,
                modifier = Modifier.padding(horizontal = Dimen.Dp18),
            )
        }
    }
}

@Composable
private fun ToastIcon(icon: ZeeToastIcon) {
    val color = ZeeTheme.colors.onAccent
    when (icon) {
        ZeeToastIcon.Check -> ZeeCheckIcon(color = color)
        ZeeToastIcon.Close -> ZeeCloseIcon(color = color)
        ZeeToastIcon.Controller -> ZeeControllerIcon(color = color)
        ZeeToastIcon.ControllerOff -> ZeeControllerIcon(color = color, crossedOut = true)
    }
}
