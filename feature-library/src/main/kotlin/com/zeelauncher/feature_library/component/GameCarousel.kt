package com.zeelauncher.feature_library.component

import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Constraints
import com.zeelauncher.core_common.constant.MINUS_ONE
import com.zeelauncher.core_common.constant.ONE
import com.zeelauncher.core_common.constant.ONE_FLOAT
import com.zeelauncher.core_common.constant.TWO
import com.zeelauncher.core_common.constant.TWO_FLOAT
import com.zeelauncher.core_common.constant.ZERO
import com.zeelauncher.core_common.constant.ZERO_FLOAT
import com.zeelauncher.core_designsystem.modifier.noRippleClickable
import com.zeelauncher.core_designsystem.sound.LocalZeeSound
import com.zeelauncher.core_designsystem.sound.ZeeSoundEffect
import com.zeelauncher.core_designsystem.sound.tapSound
import com.zeelauncher.core_designsystem.theme.ZeeDimens
import com.zeelauncher.feature_library.attr.LibraryFeatureAttribute
import com.zeelauncher.feature_library.viewmodel.LibraryItem
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.roundToInt

@Composable
internal fun GameCarousel(
    items: List<LibraryItem>,
    position: Float,
    onCardClick: (Int) -> Unit,
    onSwipe: (Int) -> Boolean,
    modifier: Modifier = Modifier,
) {
    val sound = LocalZeeSound.current
    val currentOnSwipe by rememberUpdatedState(onSwipe)
    Layout(
        modifier = modifier
            .clipToBounds()
            .pointerInput(sound) {
                val step = LibraryFeatureAttribute.GameCarousel.SwipeStep.toPx()
                val flingStep = LibraryFeatureAttribute.GameCarousel.FlingVelocityPerStep.toPx()
                val tracker = VelocityTracker()
                var dragged = ZERO_FLOAT
                fun swipe(delta: Int) {
                    if (currentOnSwipe(delta)) sound.play(ZeeSoundEffect.Move)
                }
                detectHorizontalDragGestures(
                    onDragStart = {
                        dragged = ZERO_FLOAT
                        tracker.resetTracking()
                    },
                    onDragEnd = {
                        val velocity = tracker.calculateVelocity().x
                        val steps = (abs(velocity) / flingStep).toInt()
                            .coerceAtMost(LibraryFeatureAttribute.GameCarousel.MAX_FLING_STEPS)
                        if (steps > ZERO) swipe(if (velocity < ZERO_FLOAT) steps else -steps)
                    },
                ) { change, dragAmount ->
                    change.consume()
                    tracker.addPosition(change.uptimeMillis, change.position)
                    dragged += dragAmount
                    while (abs(dragged) >= step) {
                        val direction = if (dragged < ZERO_FLOAT) ONE else MINUS_ONE
                        swipe(direction)
                        dragged += direction * step
                    }
                }
            },
        content = {
            items.forEachIndexed { index, item ->
                val focus = focusOf(index, position)
                val clickModifier = Modifier.noRippleClickable(index.tapSound(position.roundToInt())) { onCardClick(index) }
                when (item) {
                    is LibraryItem.GameItem -> GameCard(item.game, focus, clickModifier)
                    LibraryItem.ImportItem -> ImportCard(focus, clickModifier)
                }
            }
        },
    ) { measurables, constraints ->
        val gap = LibraryFeatureAttribute.GameCarousel.CardGap.toPx()
        val small = LibraryFeatureAttribute.GameCarousel.CardSmall.toPx()
        val large = LibraryFeatureAttribute.GameCarousel.CardLarge.toPx()
        val sizes = items.indices.map { small + (large - small) * focusOf(it, position) }
        val placeables = measurables.mapIndexed { index, measurable ->
            val side = sizes[index].roundToInt()
            measurable.measure(Constraints.fixed(side, side))
        }
        val centers = FloatArray(sizes.size)
        var cursor = ZERO_FLOAT
        sizes.forEachIndexed { index, side ->
            centers[index] = cursor + side / TWO_FLOAT
            cursor += side + gap
        }
        val rowWidth = cursor - gap
        val availableWidth = constraints.maxWidth - ZeeDimens.ScreenPaddingHorizontal.toPx() * TWO_FLOAT
        val shift = if (rowWidth <= availableWidth) {
            (constraints.maxWidth - rowWidth) / TWO_FLOAT
        } else {
            val lower = floor(position).toInt().coerceIn(ZERO, sizes.lastIndex)
            val upper = (lower + ONE).coerceAtMost(sizes.lastIndex)
            val fraction = position - lower
            val anchor = centers[lower] + (centers[upper] - centers[lower]) * fraction
            constraints.maxWidth / TWO_FLOAT - anchor
        }
        layout(constraints.maxWidth, constraints.maxHeight) {
            placeables.forEachIndexed { index, placeable ->
                placeable.place(
                    x = (shift + centers[index] - placeable.width / TWO_FLOAT).roundToInt(),
                    y = (constraints.maxHeight - placeable.height) / TWO,
                )
            }
        }
    }
}

private fun focusOf(index: Int, position: Float): Float =
    (ONE_FLOAT - abs(index - position)).coerceIn(ZERO_FLOAT, ONE_FLOAT)
