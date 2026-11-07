package com.zeelauncher.feature_library.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zeelauncher.core_common.constant.ONE_FLOAT
import com.zeelauncher.core_designsystem.motion.ZeeMotion
import com.zeelauncher.core_designsystem.theme.ZeeDimens
import com.zeelauncher.core_input.ObserveZeeActions
import com.zeelauncher.feature_library.R
import com.zeelauncher.feature_library.component.GameCarousel
import com.zeelauncher.feature_library.component.LibraryFooter
import com.zeelauncher.feature_library.viewmodel.LibraryEffect
import com.zeelauncher.feature_library.viewmodel.LibraryUiState
import com.zeelauncher.feature_library.viewmodel.LibraryViewModel

private const val CAROUSEL_ANIMATION_LABEL = "carousel"
private const val LIBRARY_CHANGE_LABEL = "library_change"

@Composable
internal fun LibraryRoute(
    viewModel: LibraryViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    ObserveZeeActions(viewModel::onAction)

    LifecycleStartEffect(viewModel) {
        viewModel.onStart()
        onStopOrDispose { }
    }

    LaunchedEffect(viewModel) {
        viewModel.effects.collect { effect ->
            Toast.makeText(context, effect.message(context), Toast.LENGTH_SHORT).show()
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
    AnimatedContent(
        targetState = uiState,
        contentKey = { it.contentKey },
        transitionSpec = { ZeeMotion.crossfade() },
        label = LIBRARY_CHANGE_LABEL,
        modifier = modifier.fillMaxSize(),
    ) { state ->
        if (state.isLoading) {
            Box(Modifier.fillMaxSize())
        } else {
            LibraryContent(
                uiState = state,
                onCardClick = onCardClick,
                onPlayClick = onPlayClick,
                onAppInfoClick = onAppInfoClick,
            )
        }
    }
}

@Composable
private fun LibraryContent(
    uiState: LibraryUiState,
    onCardClick: (Int) -> Unit,
    onPlayClick: () -> Unit,
    onAppInfoClick: () -> Unit,
) {
    val position by animateFloatAsState(
        targetValue = uiState.selectedIndex.toFloat(),
        animationSpec = ZeeMotion.spec(),
        label = CAROUSEL_ANIMATION_LABEL,
    )
    Column(Modifier.fillMaxSize()) {
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

private fun LibraryEffect.message(context: Context): String = when (this) {
    LibraryEffect.OpenImport -> context.getString(R.string.library_toast_import)
    LibraryEffect.GameNotFound -> context.getString(R.string.library_toast_game_not_found)
}
