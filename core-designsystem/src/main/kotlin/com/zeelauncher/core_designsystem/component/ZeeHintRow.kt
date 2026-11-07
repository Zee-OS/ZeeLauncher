package com.zeelauncher.core_designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.zeelauncher.core_common.constant.ONE
import com.zeelauncher.core_designsystem.R
import com.zeelauncher.core_designsystem.theme.Dimen
import com.zeelauncher.core_designsystem.theme.ZeeTheme
import androidx.compose.animation.AnimatedContent
import com.zeelauncher.core_designsystem.motion.ZeeMotion

@Immutable
data class ZeeButtonHint(val key: String, val label: String)

private const val HINT_LABEL_ANIMATION = "hint_label"

@Composable
fun zeeBackHint(): ZeeButtonHint = ZeeButtonHint(
    key = stringResource(R.string.zee_key_b),
    label = stringResource(R.string.zee_hint_back),
)

@Composable
fun ZeeHintRow(
    hints: List<ZeeButtonHint>,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(Dimen.Dp22),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        hints.forEach { hint ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(Dimen.Dp10),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ZeeKeyBadge(hint.key)
                AnimatedContent(
                    targetState = hint.label,
                    transitionSpec = { ZeeMotion.crossfade() },
                    label = HINT_LABEL_ANIMATION,
                ) { label ->
                    Text(
                        text = label,
                        style = ZeeTheme.typography.hint,
                        color = ZeeTheme.colors.hint,
                        maxLines = ONE,
                    )
                }
            }
        }
    }
}
