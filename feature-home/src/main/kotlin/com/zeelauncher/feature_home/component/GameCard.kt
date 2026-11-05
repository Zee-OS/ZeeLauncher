package com.zeelauncher.feature_home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.lerp
import com.zeelauncher.core_common.constant.ONE
import com.zeelauncher.core_common.constant.ZERO_FLOAT
import com.zeelauncher.core_designsystem.component.ZeeFocusFrame
import com.zeelauncher.core_designsystem.icon.ZeePlusIcon
import com.zeelauncher.core_designsystem.theme.Dimen
import com.zeelauncher.core_designsystem.theme.TextDimen
import com.zeelauncher.core_designsystem.theme.ZeeTheme
import com.zeelauncher.feature_home.R
import com.zeelauncher.feature_home.viewmodel.HomeGame

@Composable
internal fun GameCard(
    game: HomeGame,
    focus: Float,
    modifier: Modifier = Modifier,
) {
    val cover = game.cover
    ZeeFocusFrame(focus = focus, modifier = modifier) {
        if (cover != null) {
            Image(
                painter = painterResource(cover),
                contentDescription = game.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )
        } else {
            Text(
                text = game.title.uppercase(),
                style = ZeeTheme.typography.button,
                color = ZeeTheme.colors.content,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(Dimen.Dp12),
            )
        }
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
                text = stringResource(R.string.home_import_title),
                style = typography.cardLabel.copy(fontSize = lerp(TextDimen.Sp16, TextDimen.Sp26, focus)),
                color = ZeeTheme.colors.content,
                maxLines = ONE,
                softWrap = false,
            )
            if (focus > ZERO_FLOAT) {
                Spacer(Modifier.height(lerp(Dimen.Dp0, Dimen.Dp16, focus)))
                Text(
                    text = stringResource(R.string.home_import_subtitle),
                    style = typography.body,
                    color = ZeeTheme.colors.contentSecondary,
                    maxLines = ONE,
                    softWrap = false,
                    modifier = Modifier.alpha(focus),
                )
            }
        }
    }
}
