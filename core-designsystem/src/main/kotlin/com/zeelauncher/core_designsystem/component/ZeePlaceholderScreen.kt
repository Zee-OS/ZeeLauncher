package com.zeelauncher.core_designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.zeelauncher.core_common.constant.ONE
import com.zeelauncher.core_common.constant.ONE_FLOAT
import com.zeelauncher.core_designsystem.theme.ZeeDimens
import com.zeelauncher.core_designsystem.theme.ZeeTheme

@Composable
fun ZeePlaceholderScreen(
    message: String,
    hints: List<ZeeButtonHint>,
    modifier: Modifier = Modifier,
) {
    Column(modifier.fillMaxSize()) {
        Box(
            Modifier
                .weight(ONE_FLOAT)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = message,
                style = ZeeTheme.typography.headline,
                color = ZeeTheme.colors.contentSecondary,
                maxLines = ONE,
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = ZeeDimens.ScreenPaddingHorizontal),
            horizontalArrangement = Arrangement.End,
        ) {
            ZeeHintRow(hints)
        }
    }
}
