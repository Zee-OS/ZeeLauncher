package com.zeelauncher.core_designsystem.component

import androidx.compose.animation.AnimatedContent
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
import com.zeelauncher.core_designsystem.attr.DesignSystemAttribute
import com.zeelauncher.core_designsystem.motion.ZeeMotion
import com.zeelauncher.core_designsystem.theme.Dimen
import com.zeelauncher.core_designsystem.theme.ZeeTheme

@Immutable
data class ZeeButtonHint(val key: String, val label: String)

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
                    label = DesignSystemAttribute.HintRow.LABEL_ANIMATION,
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
