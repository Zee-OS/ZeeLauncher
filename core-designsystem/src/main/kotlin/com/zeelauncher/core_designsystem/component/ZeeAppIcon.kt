package com.zeelauncher.core_designsystem.component

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.TextUnit
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.decode.DataSource
import com.zeelauncher.core_common.constant.ONE_FLOAT
import com.zeelauncher.core_common.constant.ZERO_FLOAT
import com.zeelauncher.core_common.text.toAbbreviation
import com.zeelauncher.core_designsystem.motion.ZeeMotion
import com.zeelauncher.core_model.app.AppIcon

@Composable
fun ZeeAppIcon(
    icon: AppIcon,
    label: String,
    placeholderLabelSize: TextUnit,
    modifier: Modifier = Modifier,
) {
    val abbreviation = remember(label) { label.toAbbreviation() }
    var iconState by remember(icon) { mutableStateOf<AsyncImagePainter.State>(AsyncImagePainter.State.Empty) }
    val iconAlpha = remember(icon) { Animatable(ZERO_FLOAT) }

    LaunchedEffect(iconState) {
        val state = iconState
        if (state is AsyncImagePainter.State.Success) {
            if (state.result.dataSource == DataSource.MEMORY_CACHE) {
                iconAlpha.snapTo(ONE_FLOAT)
            } else {
                iconAlpha.animateTo(ONE_FLOAT, ZeeMotion.spec())
            }
        }
    }

    Box(modifier.fillMaxSize()) {
        if (iconAlpha.value < ONE_FLOAT) {
            ZeePlaceholderCover(label = abbreviation, labelSize = placeholderLabelSize)
        }
        AsyncImage(
            model = icon,
            contentDescription = label,
            contentScale = ContentScale.Crop,
            onState = { iconState = it },
            modifier = Modifier
                .fillMaxSize()
                .alpha(iconAlpha.value),
        )
    }
}
