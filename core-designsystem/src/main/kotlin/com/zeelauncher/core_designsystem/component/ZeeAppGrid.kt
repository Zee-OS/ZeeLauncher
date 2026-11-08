package com.zeelauncher.core_designsystem.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import com.zeelauncher.core_common.constant.ONE
import com.zeelauncher.core_common.constant.ONE_FLOAT
import com.zeelauncher.core_common.constant.ZERO_FLOAT
import com.zeelauncher.core_designsystem.modifier.noRippleClickable
import com.zeelauncher.core_designsystem.motion.ZeeMotion
import com.zeelauncher.core_designsystem.sound.tapSound
import com.zeelauncher.core_designsystem.theme.Dimen
import com.zeelauncher.core_designsystem.theme.ZeeTheme
import com.zeelauncher.core_model.app.AppIcon
import com.zeelauncher.core_model.app.InstalledApp

private const val TILE_FOCUS_LABEL = "app_grid_tile_focus"

@Immutable
data class ZeeAppGridSpec(
    val columns: Int,
    val visibleRows: Int,
    val tileSize: Dp,
    val cellWidth: Dp,
    val cellHeight: Dp,
    val columnSpacing: Dp,
    val rowSpacing: Dp,
    val labelSpacing: Dp,
    val labelSize: TextUnit,
    val labelLines: Int,
    val placeholderLabelSize: TextUnit,
    val tileTopInset: Dp = Dimen.Dp0,
) {
    val width: Dp get() = cellWidth * columns + columnSpacing * (columns - ONE)
    val height: Dp get() = cellHeight * visibleRows + rowSpacing * (visibleRows - ONE)
}

@Composable
fun ZeeAppGrid(
    apps: List<InstalledApp>,
    selectedIndex: Int,
    spec: ZeeAppGridSpec,
    onTileClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val gridState = rememberLazyGridState()
    val rowStridePx = with(LocalDensity.current) { (spec.cellHeight + spec.rowSpacing).toPx() }

    LaunchedEffect(selectedIndex, spec) {
        val selectedRow = selectedIndex / spec.columns
        val firstVisibleRow = gridState.firstVisibleItemIndex / spec.columns
        val targetFirstRow = when {
            selectedRow < firstVisibleRow -> selectedRow
            selectedRow >= firstVisibleRow + spec.visibleRows -> selectedRow - spec.visibleRows + ONE
            else -> return@LaunchedEffect
        }
        val currentOffset = firstVisibleRow * rowStridePx + gridState.firstVisibleItemScrollOffset
        gridState.animateScrollBy(targetFirstRow * rowStridePx - currentOffset, ZeeMotion.spec())
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(spec.columns),
        state = gridState,
        horizontalArrangement = Arrangement.spacedBy(spec.columnSpacing),
        verticalArrangement = Arrangement.spacedBy(spec.rowSpacing),
        modifier = modifier.size(spec.width, spec.height),
    ) {
        itemsIndexed(apps, key = { _, app -> app.packageName }) { index, app ->
            ZeeAppTile(
                app = app,
                selected = index == selectedIndex,
                spec = spec,
                modifier = Modifier.noRippleClickable(index.tapSound(selectedIndex)) { onTileClick(index) },
            )
        }
    }
}

@Composable
private fun ZeeAppTile(
    app: InstalledApp,
    selected: Boolean,
    spec: ZeeAppGridSpec,
    modifier: Modifier = Modifier,
) {
    val focus by animateFloatAsState(
        targetValue = if (selected) ONE_FLOAT else ZERO_FLOAT,
        animationSpec = ZeeMotion.spec(),
        label = TILE_FOCUS_LABEL,
    )
    val icon = remember(app.packageName, app.activityName) { AppIcon(app.packageName, app.activityName) }
    val colors = ZeeTheme.colors
    Column(
        modifier = modifier
            .width(spec.cellWidth)
            .height(spec.cellHeight)
            .padding(top = spec.tileTopInset),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ZeeFocusFrame(focus = focus, modifier = Modifier.size(spec.tileSize)) {
            ZeeAppIcon(
                icon = icon,
                label = app.label,
                placeholderLabelSize = spec.placeholderLabelSize,
            )
        }
        Spacer(Modifier.height(spec.labelSpacing))
        Text(
            text = app.label,
            style = ZeeTheme.typography.hint.copy(fontSize = spec.labelSize),
            color = if (selected) colors.title else colors.hint,
            textAlign = TextAlign.Center,
            maxLines = spec.labelLines,
            minLines = spec.labelLines,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
