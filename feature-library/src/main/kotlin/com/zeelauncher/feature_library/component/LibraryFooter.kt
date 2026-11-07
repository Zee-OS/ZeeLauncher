package com.zeelauncher.feature_library.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.style.TextOverflow
import com.zeelauncher.core_common.constant.ONE
import com.zeelauncher.core_common.constant.ONE_FLOAT
import com.zeelauncher.core_common.constant.SPACE
import com.zeelauncher.core_common.constant.ZERO_FLOAT
import com.zeelauncher.core_designsystem.component.ZeeButton
import com.zeelauncher.core_designsystem.component.ZeeButtonHint
import com.zeelauncher.core_designsystem.component.ZeeButtonVariant
import com.zeelauncher.core_designsystem.component.ZeeHintRow
import com.zeelauncher.core_designsystem.component.zeeBackHint
import com.zeelauncher.core_designsystem.motion.ZeeMotion
import com.zeelauncher.core_designsystem.theme.Dimen
import com.zeelauncher.core_designsystem.theme.ZeeTheme
import com.zeelauncher.core_model.game.GamePlatform
import com.zeelauncher.feature_library.R
import com.zeelauncher.feature_library.viewmodel.LibraryItem
import com.zeelauncher.core_designsystem.R as DesignR

private const val FOOTER_INFO_LABEL = "footer_info"

@Composable
internal fun LibraryFooter(
    item: LibraryItem,
    hasGames: Boolean,
    onPlayClick: () -> Unit,
    onAppInfoClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
    ) {
        Box(
            modifier = Modifier.weight(ONE_FLOAT),
            contentAlignment = Alignment.BottomStart,
        ) {
            GameInfo(
                title = SPACE,
                meta = SPACE,
                onPlayClick = {},
                onAppInfoClick = {},
                modifier = Modifier
                    .alpha(ZERO_FLOAT)
                    .clearAndSetSemantics {},
            )
            AnimatedContent(
                targetState = item,
                contentKey = { it.footerVariant(hasGames) },
                contentAlignment = Alignment.BottomStart,
                transitionSpec = { ZeeMotion.crossfade() },
                label = FOOTER_INFO_LABEL,
            ) { target ->
                when {
                    target is LibraryItem.GameItem -> GameInfo(
                        title = target.game.label.uppercase(),
                        meta = stringResource(target.game.platform.labelRes()),
                        onPlayClick = onPlayClick,
                        onAppInfoClick = onAppInfoClick,
                    )
                    hasGames -> FooterInfo(
                        title = stringResource(R.string.library_import_title),
                        meta = stringResource(R.string.library_import_meta),
                    )
                    else -> FooterInfo(
                        title = stringResource(R.string.library_empty_title),
                        meta = stringResource(R.string.library_empty_meta),
                    )
                }
            }
        }
        ZeeHintRow(
            listOf(
                ZeeButtonHint(
                    key = stringResource(DesignR.string.zee_key_a),
                    label = stringResource(
                        if (item is LibraryItem.GameItem) R.string.library_hint_launch else R.string.library_hint_import,
                    ),
                ),
                zeeBackHint(),
            ),
        )
    }
}

private fun GamePlatform.labelRes(): Int = when (this) {
    GamePlatform.Android -> R.string.library_platform_android
}

private enum class FooterVariant { Game, Import, Empty }

private fun LibraryItem.footerVariant(hasGames: Boolean): FooterVariant = when {
    this is LibraryItem.GameItem -> FooterVariant.Game
    hasGames -> FooterVariant.Import
    else -> FooterVariant.Empty
}

@Composable
private fun GameInfo(
    title: String,
    meta: String,
    onPlayClick: () -> Unit,
    onAppInfoClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        FooterInfo(title = title, meta = meta)
        Spacer(Modifier.height(Dimen.Dp14))
        Row(horizontalArrangement = Arrangement.spacedBy(Dimen.Dp14)) {
            ZeeButton(
                label = stringResource(R.string.library_play),
                onClick = onPlayClick,
            )
            ZeeButton(
                label = stringResource(R.string.library_app_info),
                onClick = onAppInfoClick,
                variant = ZeeButtonVariant.Secondary,
            )
        }
    }
}

@Composable
private fun FooterInfo(
    title: String,
    meta: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
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
}
