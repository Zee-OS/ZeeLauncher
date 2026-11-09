package com.zeelauncher.feature_apps.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalResources
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zeelauncher.core_common.constant.ONE_FLOAT
import com.zeelauncher.core_designsystem.component.ZeeAppGrid
import com.zeelauncher.core_designsystem.motion.ZeeMotion
import com.zeelauncher.core_designsystem.theme.ZeeDimens
import com.zeelauncher.core_designsystem.toast.LocalZeeToast
import com.zeelauncher.core_designsystem.toast.ZeeToastIcon
import com.zeelauncher.core_input.ObserveZeeActions
import com.zeelauncher.feature_apps.R
import com.zeelauncher.feature_apps.attr.AppsFeatureAttribute
import com.zeelauncher.feature_apps.component.AppsFooter
import com.zeelauncher.feature_apps.viewmodel.AppsEffect
import com.zeelauncher.feature_apps.viewmodel.AppsUiState
import com.zeelauncher.feature_apps.viewmodel.AppsViewModel

@Composable
internal fun AppsRoute(
    viewModel: AppsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val resources = LocalResources.current
    val toast = LocalZeeToast.current

    ObserveZeeActions(viewModel::onAction)

    LaunchedEffect(viewModel) {
        viewModel.effects.collect { effect ->
            when (effect) {
                AppsEffect.AppNotFound -> toast.show(resources.getString(R.string.apps_toast_app_not_found), ZeeToastIcon.Close)
            }
        }
    }

    AppsScreen(
        uiState = uiState,
        onTileClick = viewModel::onTileClick,
        onOpenClick = viewModel::open,
    )
}

@Composable
internal fun AppsScreen(
    uiState: AppsUiState,
    onTileClick: (Int) -> Unit,
    onOpenClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AnimatedContent(
        targetState = uiState,
        contentKey = { it.isLoading },
        transitionSpec = { ZeeMotion.crossfade() },
        label = AppsFeatureAttribute.Screen.CONTENT_LABEL,
        modifier = modifier.fillMaxSize(),
    ) { state ->
        if (state.isLoading) {
            Box(Modifier.fillMaxSize())
        } else {
            Column(Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .weight(ONE_FLOAT)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    ZeeAppGrid(
                        apps = state.apps,
                        selectedIndex = state.selectedIndex,
                        spec = AppsFeatureAttribute.Grid.Spec,
                        onTileClick = onTileClick,
                    )
                }
                AppsFooter(
                    app = state.selectedApp,
                    onOpenClick = onOpenClick,
                    modifier = Modifier.padding(horizontal = ZeeDimens.ScreenPaddingHorizontal),
                )
            }
        }
    }
}
