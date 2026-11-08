package com.zeelauncher.feature_library.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.zeelauncher.core_common.constant.ONE
import com.zeelauncher.core_common.constant.ONE_FLOAT
import com.zeelauncher.core_common.constant.TWO
import com.zeelauncher.core_common.constant.ZERO_FLOAT
import com.zeelauncher.core_designsystem.component.ZeeAppIcon
import com.zeelauncher.core_designsystem.component.ZeeButtonHint
import com.zeelauncher.core_designsystem.component.ZeeFocusFrame
import com.zeelauncher.core_designsystem.component.ZeeHintRow
import com.zeelauncher.core_designsystem.modifier.noRippleClickable
import com.zeelauncher.core_designsystem.motion.ZeeMotion
import com.zeelauncher.core_designsystem.theme.Dimen
import com.zeelauncher.core_designsystem.theme.TextDimen
import com.zeelauncher.core_designsystem.theme.ZeeTheme
import com.zeelauncher.core_model.app.AppIcon
import com.zeelauncher.core_model.app.InstalledApp
import com.zeelauncher.feature_library.R
import com.zeelauncher.feature_library.viewmodel.IMPORT_GRID_COLUMNS
import com.zeelauncher.feature_library.viewmodel.ImportUiState
import com.zeelauncher.core_designsystem.R as DesignR

private const val VISIBLE_ROWS = 2
private const val TILE_FOCUS_LABEL = "import_tile_focus"

private val TileSize = Dimen.Dp80
private val CellWidth = Dimen.Dp108
private val CellHeight = Dimen.Dp124
private val TileLabelSpacing = Dimen.Dp10
private val ColumnSpacing = Dimen.Dp20
private val RowSpacing = Dimen.Dp20

@Composable
internal fun ImportDialogContent(
    state: ImportUiState,
    onTileClick: (Int) -> Unit,
) {
    Text(
        text = stringResource(R.string.library_import_title),
        style = ZeeTheme.typography.headline,
        color = ZeeTheme.colors.title,
        maxLines = ONE,
    )
    Spacer(Modifier.height(Dimen.Dp30))
    val gridWidth = CellWidth * IMPORT_GRID_COLUMNS + ColumnSpacing * (IMPORT_GRID_COLUMNS - ONE)
    val gridHeight = CellHeight * VISIBLE_ROWS + RowSpacing * (VISIBLE_ROWS - ONE)
    Box(
        modifier = Modifier.size(gridWidth, gridHeight),
        contentAlignment = Alignment.Center,
    ) {
        when {
            state.isLoading -> Unit
            state.apps.isEmpty() -> Text(
                text = stringResource(R.string.library_import_empty),
                style = ZeeTheme.typography.label,
                color = ZeeTheme.colors.contentSecondary,
                textAlign = TextAlign.Center,
            )
            else -> ImportGrid(
                apps = state.apps,
                selectedIndex = state.selectedIndex,
                onTileClick = onTileClick,
            )
        }
    }
    Spacer(Modifier.height(Dimen.Dp30))
    ZeeHintRow(
        listOf(
            ZeeButtonHint(stringResource(DesignR.string.zee_key_a), stringResource(R.string.library_hint_import)),
            ZeeButtonHint(stringResource(DesignR.string.zee_key_b), stringResource(R.string.library_hint_cancel)),
        ),
    )
}

@Composable
private fun ImportGrid(
    apps: List<InstalledApp>,
    selectedIndex: Int,
    onTileClick: (Int) -> Unit,
) {
    val gridState = rememberLazyGridState()
    val rowStridePx = with(LocalDensity.current) { (CellHeight + RowSpacing).toPx() }

    LaunchedEffect(selectedIndex) {
        val selectedRow = selectedIndex / IMPORT_GRID_COLUMNS
        val firstVisibleRow = gridState.firstVisibleItemIndex / IMPORT_GRID_COLUMNS
        val targetFirstRow = when {
            selectedRow < firstVisibleRow -> selectedRow
            selectedRow >= firstVisibleRow + VISIBLE_ROWS -> selectedRow - VISIBLE_ROWS + ONE
            else -> return@LaunchedEffect
        }
        val currentOffset = firstVisibleRow * rowStridePx + gridState.firstVisibleItemScrollOffset
        gridState.animateScrollBy(targetFirstRow * rowStridePx - currentOffset, ZeeMotion.spec())
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(IMPORT_GRID_COLUMNS),
        state = gridState,
        horizontalArrangement = Arrangement.spacedBy(ColumnSpacing),
        verticalArrangement = Arrangement.spacedBy(RowSpacing),
    ) {
        itemsIndexed(apps, key = { _, app -> app.packageName }) { index, app ->
            ImportTile(
                app = app,
                selected = index == selectedIndex,
                modifier = Modifier.noRippleClickable { onTileClick(index) },
            )
        }
    }
}

@Composable
private fun ImportTile(
    app: InstalledApp,
    selected: Boolean,
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
            .width(CellWidth)
            .height(CellHeight),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ZeeFocusFrame(focus = focus, modifier = Modifier.size(TileSize)) {
            ZeeAppIcon(
                icon = icon,
                label = app.label,
                placeholderLabelSize = TextDimen.Sp20,
            )
        }
        Spacer(Modifier.height(TileLabelSpacing))
        Text(
            text = app.label,
            style = ZeeTheme.typography.hint.copy(fontSize = TextDimen.Sp14),
            color = if (selected) colors.title else colors.hint,
            textAlign = TextAlign.Center,
            maxLines = TWO,
            minLines = TWO,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
