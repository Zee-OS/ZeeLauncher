package com.zeelauncher.core_designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.zeelauncher.core_designsystem.modifier.hardShadow
import com.zeelauncher.core_designsystem.modifier.noRippleClickable
import com.zeelauncher.core_designsystem.theme.ZeeDimens
import com.zeelauncher.core_designsystem.theme.ZeeTheme

@Composable
fun ZeeModal(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    val colors = ZeeTheme.colors
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        ZeeScrim(onDismiss = onDismiss)
        Column(
            modifier = modifier
                .hardShadow(ZeeDimens.ShadowFocused, colors.accent)
                .background(colors.background)
                .border(ZeeDimens.BorderThick, colors.content)
                .noRippleClickable(null) {}
                .padding(ZeeDimens.ModalPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            content = content,
        )
    }
}
