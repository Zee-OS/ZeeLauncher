package com.zeelauncher.core_designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.zeelauncher.core_common.constant.ONE
import com.zeelauncher.core_designsystem.theme.Dimen
import com.zeelauncher.core_designsystem.theme.ZeeTheme

@Immutable
data class ZeeButtonHint(val key: String, val label: String)

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
                Text(
                    text = hint.label,
                    style = ZeeTheme.typography.hint,
                    color = ZeeTheme.colors.hint,
                    maxLines = ONE,
                )
            }
        }
    }
}
