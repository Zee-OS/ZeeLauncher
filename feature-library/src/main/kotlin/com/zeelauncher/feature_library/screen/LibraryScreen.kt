package com.zeelauncher.feature_library.screen

import android.widget.Toast
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zeelauncher.core_common.constant.ONE_FLOAT
import com.zeelauncher.core_designsystem.theme.ZeeDimens
import com.zeelauncher.core_input.ObserveZeeActions
import com.zeelauncher.feature_library.R
import com.zeelauncher.feature_library.component.GameCarousel
import com.zeelauncher.feature_library.component.LibraryFooter
import com.zeelauncher.feature_library.viewmodel.LibraryEffect
import com.zeelauncher.feature_library.viewmodel.LibraryUiState
import com.zeelauncher.feature_library.viewmodel.LibraryViewModel

private const val CAROUSEL_ANIMATION_MILLIS = 260
private const val CAROUSEL_ANIMATION_LABEL = "carousel"

@Composable
internal fun LibraryRoute(
    viewModel: LibraryViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    ObserveZeeActions(viewModel::onAction)

    LaunchedEffect(viewModel) {
        viewModel.effects.collect { effect ->
            val message = when (effect) {
                LibraryEffect.OpenImport -> context.getString(R.string.library_toast_import)
                LibraryEffect.GameNotFound -> context.getString(R.string.library_toast_game_not_found)
            }
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    LibraryScreen(
        uiState = uiState,
        onCardClick = viewModel::onCardClick,
        onPlayClick = viewModel::confirm,
        onAppInfoClick = viewModel::onAppInfoClick,
    )
}

@Composable
internal fun LibraryScreen(
    uiState: LibraryUiState,
    onCardClick: (Int) -> Unit,
    onPlayClick: () -> Unit,
    onAppInfoClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (uiState.isLoading) {
        Box(modifier.fillMaxSize())
        return
    }
    val position by animateFloatAsState(
        targetValue = uiState.selectedIndex.toFloat(),
        animationSpec = tween(
            durationMillis = CAROUSEL_ANIMATION_MILLIS,
            easing = FastOutSlowInEasing,
        ),
        label = CAROUSEL_ANIMATION_LABEL,
    )
    Column(modifier.fillMaxSize()) {
        GameCarousel(
            items = uiState.items,
            position = position,
            onCardClick = onCardClick,
            modifier = Modifier
                .weight(ONE_FLOAT)
                .fillMaxWidth(),
        )
        LibraryFooter(
            item = uiState.selectedItem,
            hasGames = uiState.hasGames,
            onPlayClick = onPlayClick,
            onAppInfoClick = onAppInfoClick,
            modifier = Modifier.padding(horizontal = ZeeDimens.ScreenPaddingHorizontal),
        )
    }
}
