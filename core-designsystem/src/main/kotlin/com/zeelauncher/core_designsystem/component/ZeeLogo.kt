package com.zeelauncher.core_designsystem.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.zeelauncher.core_common.constant.ONE
import com.zeelauncher.core_designsystem.R
import com.zeelauncher.core_designsystem.theme.ZeeTheme

@Composable
fun ZeeLogo(modifier: Modifier = Modifier) {
    val colors = ZeeTheme.colors
    val prefix = stringResource(R.string.zee_logo_prefix)
    val suffix = stringResource(R.string.zee_logo_suffix)
    Text(
        text = buildAnnotatedString {
            withStyle(SpanStyle(color = colors.accent)) { append(prefix) }
            withStyle(SpanStyle(color = colors.content)) { append(suffix) }
        },
        style = ZeeTheme.typography.logo,
        maxLines = ONE,
        modifier = modifier,
    )
}
