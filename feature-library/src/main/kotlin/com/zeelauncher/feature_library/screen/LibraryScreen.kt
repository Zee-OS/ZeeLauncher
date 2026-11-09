package com.zeelauncher.feature_library.screen

import android.content.Context
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
import com.zeelauncher.core_common.constant.ZERO
import com.zeelauncher.core_designsystem.component.ZeeDrawer
import com.zeelauncher.core_designsystem.component.ZeeModal
import com.zeelauncher.core_designsystem.motion.ZeeMotion
import com.zeelauncher.core_designsystem.overlay.ZeeOverlay
import com.zeelauncher.core_designsystem.overlay.isOverlayActive
import com.zeelauncher.core_designsystem.theme.ZeeDimens
import com.zeelauncher.core_designsystem.toast.LocalZeeToast
import com.zeelauncher.core_designsystem.toast.ZeeToastHostState
import com.zeelauncher.core_designsystem.toast.ZeeToastIcon
import com.zeelauncher.core_input.ObserveZeeActions
import com.zeelauncher.feature_library.R
import com.zeelauncher.feature_library.attr.LibraryFeatureAttribute
import com.zeelauncher.feature_library.component.AppInfoDrawerContent
import com.zeelauncher.feature_library.component.GameCarousel
import com.zeelauncher.feature_library.component.ImportDialogContent
import com.zeelauncher.feature_library.component.LibraryFooter
import com.zeelauncher.feature_library.viewmodel.LibraryEffect
import com.zeelauncher.feature_library.viewmodel.LibraryUiState
import com.zeelauncher.feature_library.viewmodel.LibraryViewModel

@Composable
internal fun LibraryRoute(
    viewModel: LibraryViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val toast = LocalZeeToast.current

    ObserveZeeActions(viewModel::onAction)

    LifecycleStartEffect(viewModel) {
        viewModel.onStart()
        onStopOrDispose { }
    }

    LaunchedEffect(viewModel) {
        viewModel.effects.collect { effect -> toast.showEffect(effect, context) }
    }

    LibraryScreen(
        uiState = uiState,
        onCardClick = viewModel::onCardClick,
        onPlayClick = viewModel::confirm,
        onAppInfoClick = viewModel::onAppInfoClick,
    )

    ZeeOverlay(visible = uiState.importDialog.isVisible) {
        val isActive = isOverlayActive
        ObserveZeeActions { action -> isActive && viewModel.onImportAction(action) }
        ZeeModal(onDismiss = viewModel::closeImport) {
            ImportDialogContent(
                state = uiState.importDialog,
                onTileClick = viewModel::onImportTileClick,
            )
        }
    }

    ZeeOverlay(visible = uiState.appInfo.isVisible) {
        val isActive = isOverlayActive
        ObserveZeeActions { action -> isActive && viewModel.onAppInfoAction(action) }
        ZeeDrawer(onDismiss = viewModel::closeAppInfo) {
            AppInfoDrawerContent(
                state = uiState.appInfo,
                onOptionClick = viewModel::onAppInfoOptionClick,
            )
        }
    }
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
        label = LibraryFeatureAttribute.Screen.LIBRARY_CHANGE_LABEL,
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
        label = LibraryFeatureAttribute.Screen.CAROUSEL_ANIMATION_LABEL,
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

private fun ZeeToastHostState.showEffect(effect: LibraryEffect, context: Context) {
    when (effect) {
        LibraryEffect.GameImported -> show(context.getString(R.string.library_toast_game_imported), ZeeToastIcon.Check)
        LibraryEffect.GameRemoved -> show(context.getString(R.string.library_toast_game_removed), ZeeToastIcon.Close)
        LibraryEffect.GameNotFound -> show(context.getString(R.string.library_toast_game_not_found), ZeeToastIcon.Close)
        is LibraryEffect.LibraryUpdated -> {
            if (effect.addedCount > ZERO) {
                show(
                    context.resources.getQuantityString(
                        R.plurals.library_toast_games_added,
                        effect.addedCount,
                        effect.addedCount,
                    ),
                    ZeeToastIcon.Check,
                )
            }
            if (effect.removedCount > ZERO) {
                show(
                    context.resources.getQuantityString(
                        R.plurals.library_toast_games_uninstalled,
                        effect.removedCount,
                        effect.removedCount,
                    ),
                    ZeeToastIcon.Close,
                )
            }
        }
    }
}
