package com.zeelauncher.feature_home.screen

import android.widget.Toast
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zeelauncher.core_common.constant.ONE_FLOAT
import com.zeelauncher.core_designsystem.component.ZeeButtonHint
import com.zeelauncher.core_designsystem.component.ZeeHintRow
import com.zeelauncher.core_designsystem.theme.ZeeDimens
import com.zeelauncher.core_designsystem.theme.ZeeTheme
import com.zeelauncher.core_input.ZeeAction
import com.zeelauncher.feature_home.R
import com.zeelauncher.feature_home.component.GameCarousel
import com.zeelauncher.feature_home.component.HomeFooter
import com.zeelauncher.feature_home.component.HomeHeader
import com.zeelauncher.feature_home.viewmodel.HomeEffect
import com.zeelauncher.feature_home.viewmodel.HomeTab
import com.zeelauncher.feature_home.viewmodel.HomeUiState
import com.zeelauncher.feature_home.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.Flow
import com.zeelauncher.core_designsystem.R as DesignR

private const val CAROUSEL_ANIMATION_MILLIS = 260
private const val CAROUSEL_ANIMATION_LABEL = "carousel"

@Composable
fun HomeRoute(
    actions: Flow<ZeeAction>,
    viewModel: HomeViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(actions) {
        actions.collect { viewModel.onAction(it) }
    }

    LaunchedEffect(viewModel) {
        viewModel.effects.collect { effect ->
            val message = when (effect) {
                is HomeEffect.Launch -> context.getString(R.string.home_toast_launch, effect.title)
                is HomeEffect.AppInfo -> context.getString(R.string.home_toast_app_info, effect.title)
                HomeEffect.OpenImport -> context.getString(R.string.home_toast_import)
                HomeEffect.Search -> context.getString(R.string.home_toast_search)
            }
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    HomeScreen(
        uiState = uiState,
        onTabClick = viewModel::onTabClick,
        onCardClick = viewModel::onCardClick,
        onPlayClick = viewModel::confirm,
        onAppInfoClick = viewModel::onAppInfoClick,
    )
}

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onTabClick: (HomeTab) -> Unit,
    onCardClick: (Int) -> Unit,
    onPlayClick: () -> Unit,
    onAppInfoClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val horizontalPadding = Modifier.padding(horizontal = ZeeDimens.ScreenPaddingHorizontal)
    Column(
        modifier
            .fillMaxSize()
            .padding(vertical = ZeeDimens.ScreenPaddingVertical),
    ) {
        HomeHeader(
            selectedTab = uiState.tab,
            onTabClick = onTabClick,
            modifier = horizontalPadding,
        )
        if (uiState.tab == HomeTab.Library) {
            val position by animateFloatAsState(
                targetValue = uiState.selectedIndex.toFloat(),
                animationSpec = tween(
                    durationMillis = CAROUSEL_ANIMATION_MILLIS,
                    easing = FastOutSlowInEasing,
                ),
                label = CAROUSEL_ANIMATION_LABEL,
            )
            GameCarousel(
                items = uiState.items,
                position = position,
                onCardClick = onCardClick,
                modifier = Modifier
                    .weight(ONE_FLOAT)
                    .fillMaxWidth(),
            )
            HomeFooter(
                item = uiState.selectedItem,
                hasGames = uiState.hasGames,
                onPlayClick = onPlayClick,
                onAppInfoClick = onAppInfoClick,
                modifier = horizontalPadding,
            )
        } else {
            Box(
                Modifier
                    .weight(ONE_FLOAT)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = stringResource(R.string.home_coming_soon, stringResource(uiState.tab.labelRes)),
                    style = ZeeTheme.typography.headline,
                    color = ZeeTheme.colors.contentSecondary,
                )
            }
            Row(
                modifier = horizontalPadding.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                ZeeHintRow(
                    listOf(
                        ZeeButtonHint(
                            stringResource(DesignR.string.zee_key_b),
                            stringResource(R.string.home_hint_back),
                        ),
                    ),
                )
            }
        }
    }
}
