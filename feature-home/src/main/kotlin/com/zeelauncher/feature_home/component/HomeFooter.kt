package com.zeelauncher.feature_home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.zeelauncher.core_common.constant.ONE
import com.zeelauncher.core_common.constant.ONE_FLOAT
import com.zeelauncher.core_designsystem.component.ZeeButton
import com.zeelauncher.core_designsystem.component.ZeeButtonHint
import com.zeelauncher.core_designsystem.component.ZeeButtonVariant
import com.zeelauncher.core_designsystem.component.ZeeHintRow
import com.zeelauncher.core_designsystem.theme.Dimen
import com.zeelauncher.core_designsystem.theme.ZeeTheme
import com.zeelauncher.feature_home.R
import com.zeelauncher.feature_home.viewmodel.HomeCarouselItem
import com.zeelauncher.core_designsystem.R as DesignR

@Composable
internal fun HomeFooter(
    item: HomeCarouselItem,
    hasGames: Boolean,
    onPlayClick: () -> Unit,
    onAppInfoClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
    ) {
        Column(Modifier.weight(ONE_FLOAT)) {
            when {
                item is HomeCarouselItem.Game -> {
                    FooterInfo(
                        title = item.game.title.uppercase(),
                        meta = stringResource(R.string.home_game_meta, item.game.genre),
                    )
                    Spacer(Modifier.height(Dimen.Dp14))
                    Row(horizontalArrangement = Arrangement.spacedBy(Dimen.Dp14)) {
                        ZeeButton(
                            label = stringResource(R.string.home_play),
                            onClick = onPlayClick,
                        )
                        ZeeButton(
                            label = stringResource(R.string.home_app_info),
                            onClick = onAppInfoClick,
                            variant = ZeeButtonVariant.Secondary,
                        )
                    }
                }
                hasGames -> FooterInfo(
                    title = stringResource(R.string.home_import_title),
                    meta = stringResource(R.string.home_import_meta),
                )
                else -> FooterInfo(
                    title = stringResource(R.string.home_empty_title),
                    meta = stringResource(R.string.home_empty_meta),
                )
            }
        }
        ZeeHintRow(if (item is HomeCarouselItem.Game) gameHints() else importHints())
    }
}

@Composable
private fun gameHints(): List<ZeeButtonHint> = listOf(
    ZeeButtonHint(stringResource(DesignR.string.zee_key_a), stringResource(R.string.home_hint_launch)),
    ZeeButtonHint(stringResource(DesignR.string.zee_key_b), stringResource(R.string.home_hint_back)),
    ZeeButtonHint(stringResource(DesignR.string.zee_key_y), stringResource(R.string.home_hint_search)),
)

@Composable
private fun importHints(): List<ZeeButtonHint> = listOf(
    ZeeButtonHint(stringResource(DesignR.string.zee_key_a), stringResource(R.string.home_hint_import)),
    ZeeButtonHint(stringResource(DesignR.string.zee_key_b), stringResource(R.string.home_hint_back)),
)

@Composable
private fun FooterInfo(title: String, meta: String) {
    Text(
        text = title,
        style = ZeeTheme.typography.display,
        color = ZeeTheme.colors.title,
        maxLines = ONE,
        overflow = TextOverflow.Ellipsis,
    )
    Spacer(Modifier.height(Dimen.Dp6))
    Text(
        text = meta,
        style = ZeeTheme.typography.label,
        color = ZeeTheme.colors.contentSecondary,
        maxLines = ONE,
        overflow = TextOverflow.Ellipsis,
    )
}
