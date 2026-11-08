package com.zeelauncher.feature_library.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Constraints
import com.zeelauncher.core_common.constant.ONE
import com.zeelauncher.core_common.constant.ONE_FLOAT
import com.zeelauncher.core_common.constant.TWO
import com.zeelauncher.core_common.constant.TWO_FLOAT
import com.zeelauncher.core_common.constant.ZERO
import com.zeelauncher.core_common.constant.ZERO_FLOAT
import com.zeelauncher.core_designsystem.modifier.noRippleClickable
import com.zeelauncher.core_designsystem.sound.tapSound
import com.zeelauncher.core_designsystem.theme.Dimen
import com.zeelauncher.core_designsystem.theme.ZeeDimens
import com.zeelauncher.feature_library.viewmodel.LibraryItem
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.roundToInt

private val CardSmall = Dimen.Dp170
private val CardLarge = Dimen.Dp340
private val CardGap = Dimen.Dp30

@Composable
internal fun GameCarousel(
    items: List<LibraryItem>,
    position: Float,
    onCardClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Layout(
        modifier = modifier.clipToBounds(),
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
        val gap = CardGap.toPx()
        val small = CardSmall.toPx()
        val large = CardLarge.toPx()
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
