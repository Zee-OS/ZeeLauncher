package com.zeelauncher.feature_library.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.lerp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import com.zeelauncher.core_common.constant.ONE
import com.zeelauncher.core_common.text.toAbbreviation
import com.zeelauncher.core_designsystem.component.ZeeFocusFrame
import com.zeelauncher.core_designsystem.component.ZeePlaceholderCover
import com.zeelauncher.core_designsystem.icon.ZeePlusIcon
import com.zeelauncher.core_designsystem.modifier.revealHeight
import com.zeelauncher.core_designsystem.theme.Dimen
import com.zeelauncher.core_designsystem.theme.TextDimen
import com.zeelauncher.core_designsystem.theme.ZeeTheme
import com.zeelauncher.core_model.app.AppIcon
import com.zeelauncher.core_model.game.Game
import com.zeelauncher.feature_library.R
import androidx.compose.animation.core.Animatable
import androidx.compose.runtime.LaunchedEffect
import coil3.decode.DataSource
import com.zeelauncher.core_common.constant.ONE_FLOAT
import com.zeelauncher.core_common.constant.ZERO_FLOAT
import com.zeelauncher.core_designsystem.motion.ZeeMotion

@Composable
internal fun GameCard(
    game: Game,
    focus: Float,
    modifier: Modifier = Modifier,
) {
    val icon = remember(game.packageName, game.activityName) {
        AppIcon(game.packageName, game.activityName)
    }
    val abbreviation = remember(game.label) { game.label.toAbbreviation() }
    var iconState by remember(icon) { mutableStateOf<AsyncImagePainter.State>(AsyncImagePainter.State.Empty) }
    val iconAlpha = remember(icon) { Animatable(ZERO_FLOAT) }

    LaunchedEffect(iconState) {
        val state = iconState
        if (state is AsyncImagePainter.State.Success) {
            if (state.result.dataSource == DataSource.MEMORY_CACHE) {
                iconAlpha.snapTo(ONE_FLOAT)
            } else {
                iconAlpha.animateTo(ONE_FLOAT, ZeeMotion.spec())
            }
        }
    }

    ZeeFocusFrame(focus = focus, modifier = modifier) {
        if (iconAlpha.value < ONE_FLOAT) {
            ZeePlaceholderCover(
                label = abbreviation,
                labelSize = lerp(TextDimen.Sp26, TextDimen.Sp48, focus),
            )
        }
        AsyncImage(
            model = icon,
            contentDescription = game.label,
            contentScale = ContentScale.Crop,
            onState = { iconState = it },
            modifier = Modifier
                .fillMaxSize()
                .alpha(iconAlpha.value),
        )
    }
}

@Composable
internal fun ImportCard(
    focus: Float,
    modifier: Modifier = Modifier,
) {
    val typography = ZeeTheme.typography
    ZeeFocusFrame(focus = focus, modifier = modifier, dashed = true) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            ZeePlusIcon(
                size = lerp(Dimen.Dp52, Dimen.Dp96, focus),
                stroke = lerp(Dimen.Dp7, Dimen.Dp13, focus),
            )
            Spacer(Modifier.height(lerp(Dimen.Dp18, Dimen.Dp26, focus)))
            Text(
                text = stringResource(R.string.library_import_title),
                style = typography.cardLabel.copy(fontSize = lerp(TextDimen.Sp16, TextDimen.Sp26, focus)),
                color = ZeeTheme.colors.content,
                maxLines = ONE,
                softWrap = false,
            )
            Spacer(Modifier.height(lerp(Dimen.Dp0, Dimen.Dp16, focus)))
            Text(
                text = stringResource(R.string.library_import_subtitle),
                style = typography.body,
                color = ZeeTheme.colors.contentSecondary,
                maxLines = ONE,
                softWrap = false,
                modifier = Modifier
                    .revealHeight(focus)
                    .alpha(focus),
            )
        }
    }
}
