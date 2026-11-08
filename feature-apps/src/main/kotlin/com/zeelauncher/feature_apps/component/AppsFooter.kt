package com.zeelauncher.feature_apps.component

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
import com.zeelauncher.core_common.constant.SPACE
import com.zeelauncher.core_designsystem.component.ZeeButton
import com.zeelauncher.core_designsystem.component.ZeeButtonHint
import com.zeelauncher.core_designsystem.component.ZeeHintRow
import com.zeelauncher.core_designsystem.component.zeeBackHint
import com.zeelauncher.core_designsystem.theme.Dimen
import com.zeelauncher.core_designsystem.theme.ZeeTheme
import com.zeelauncher.core_model.app.AppCategory
import com.zeelauncher.core_model.app.InstalledApp
import com.zeelauncher.feature_apps.R
import com.zeelauncher.core_designsystem.R as DesignR

@Composable
internal fun AppsFooter(
    app: InstalledApp?,
    onOpenClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
    ) {
        Column(Modifier.weight(ONE_FLOAT)) {
            Text(
                text = app?.label?.uppercase() ?: SPACE,
                style = ZeeTheme.typography.display,
                color = ZeeTheme.colors.title,
                maxLines = ONE,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(Modifier.height(Dimen.Dp6))
            Text(
                text = app?.let { stringResource(it.category.labelRes()) } ?: SPACE,
                style = ZeeTheme.typography.label,
                color = ZeeTheme.colors.contentSecondary,
                maxLines = ONE,
            )
            Spacer(Modifier.height(Dimen.Dp14))
            ZeeButton(
                label = stringResource(R.string.apps_open),
                onClick = onOpenClick,
            )
        }
        ZeeHintRow(
            listOf(
                ZeeButtonHint(stringResource(DesignR.string.zee_key_a), stringResource(R.string.apps_open)),
                zeeBackHint(),
            ),
        )
    }
}

private fun AppCategory.labelRes(): Int = when (this) {
    AppCategory.Game -> R.string.apps_category_game
    AppCategory.Audio -> R.string.apps_category_audio
    AppCategory.Video -> R.string.apps_category_video
    AppCategory.Image -> R.string.apps_category_image
    AppCategory.Social -> R.string.apps_category_social
    AppCategory.News -> R.string.apps_category_news
    AppCategory.Maps -> R.string.apps_category_maps
    AppCategory.Productivity -> R.string.apps_category_productivity
    AppCategory.Undefined -> R.string.apps_category_app
}
