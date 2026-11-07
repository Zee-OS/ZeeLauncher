package com.zeelauncher.core_designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import com.zeelauncher.core_common.constant.ONE
import com.zeelauncher.core_designsystem.modifier.hatch
import com.zeelauncher.core_designsystem.theme.Dimen
import com.zeelauncher.core_designsystem.theme.ZeeTheme

@Composable
fun ZeePlaceholderCover(
    label: String,
    labelSize: TextUnit,
    modifier: Modifier = Modifier,
) {
    val colors = ZeeTheme.colors
    Box(
        modifier = modifier
            .fillMaxSize()
            .hatch(colors.shadow),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = label,
            style = ZeeTheme.typography.cardLabel.copy(fontSize = labelSize),
            color = colors.contentSecondary,
            maxLines = ONE,
            softWrap = false,
            modifier = Modifier
                .background(colors.onAccent)
                .padding(horizontal = Dimen.Dp10, vertical = Dimen.Dp6),
        )
    }
}
