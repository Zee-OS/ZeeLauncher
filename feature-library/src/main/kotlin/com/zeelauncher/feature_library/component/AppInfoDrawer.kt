package com.zeelauncher.feature_library.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.zeelauncher.core_common.constant.DASH
import com.zeelauncher.core_common.constant.ONE
import com.zeelauncher.core_common.constant.ONE_FLOAT
import com.zeelauncher.core_common.constant.TWO
import com.zeelauncher.core_designsystem.R as DesignR
import com.zeelauncher.core_designsystem.component.ZeeAppIcon
import com.zeelauncher.core_designsystem.component.ZeeButtonHint
import com.zeelauncher.core_designsystem.component.ZeeHintRow
import com.zeelauncher.core_designsystem.component.ZeeListButton
import com.zeelauncher.core_designsystem.theme.TextDimen
import com.zeelauncher.core_designsystem.theme.ZeeDimens
import com.zeelauncher.core_designsystem.theme.ZeeTheme
import com.zeelauncher.core_model.app.AppIcon
import com.zeelauncher.feature_library.R
import com.zeelauncher.feature_library.attr.LibraryFeatureAttribute
import com.zeelauncher.feature_library.viewmodel.AppInfoOption
import com.zeelauncher.feature_library.viewmodel.AppInfoUiState
import com.zeelauncher.feature_library.viewmodel.RelativeTime

@Composable
internal fun ColumnScope.AppInfoDrawerContent(
    state: AppInfoUiState,
    onOptionClick: (Int) -> Unit,
) {
    val game = state.game ?: return
    val colors = ZeeTheme.colors
    val icon = remember(game.packageName, game.activityName) { AppIcon(game.packageName, game.activityName) }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            Modifier
                .size(LibraryFeatureAttribute.AppInfoDrawer.IconSize)
                .border(ZeeDimens.BorderThin, colors.content)
                .padding(ZeeDimens.BorderThin),
        ) {
            ZeeAppIcon(
                icon = icon,
                label = game.label,
                placeholderLabelSize = TextDimen.Sp20,
            )
        }
        Spacer(Modifier.width(LibraryFeatureAttribute.AppInfoDrawer.HeaderSpacing))
        Text(
            text = game.label.uppercase(),
            style = ZeeTheme.typography.headline,
            color = colors.title,
            maxLines = TWO,
            overflow = TextOverflow.Ellipsis,
        )
    }
    Spacer(Modifier.height(LibraryFeatureAttribute.AppInfoDrawer.SectionSpacing))
    InfoRow(
        label = stringResource(R.string.library_info_last_played),
        value = state.lastPlayed.label(),
    )
    Spacer(Modifier.height(LibraryFeatureAttribute.AppInfoDrawer.InfoRowSpacing))
    InfoRow(
        label = stringResource(R.string.library_info_version),
        value = state.versionName ?: DASH,
    )
    Spacer(Modifier.height(LibraryFeatureAttribute.AppInfoDrawer.SectionSpacing))
    Column(verticalArrangement = Arrangement.spacedBy(LibraryFeatureAttribute.AppInfoDrawer.OptionSpacing)) {
        state.options.forEachIndexed { index, option ->
            ZeeListButton(
                label = stringResource(option.labelRes()),
                focused = index == state.selectedIndex,
                onClick = { onOptionClick(index) },
            )
        }
    }
    Spacer(Modifier.weight(ONE_FLOAT))
    ZeeHintRow(
        listOf(
            ZeeButtonHint(stringResource(DesignR.string.zee_key_a), stringResource(R.string.library_hint_select)),
            ZeeButtonHint(stringResource(DesignR.string.zee_key_b), stringResource(R.string.library_hint_close)),
        ),
    )
}

@Composable
private fun InfoRow(
    label: String,
    value: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            style = ZeeTheme.typography.label,
            color = ZeeTheme.colors.contentSecondary,
            maxLines = ONE,
        )
        Text(
            text = value,
            style = ZeeTheme.typography.button,
            color = ZeeTheme.colors.title,
            maxLines = ONE,
        )
    }
}

private fun AppInfoOption.labelRes(): Int = when (this) {
    AppInfoOption.Play -> R.string.library_play
    AppInfoOption.Remove -> R.string.library_option_remove
    AppInfoOption.Uninstall -> R.string.library_option_uninstall
}

@Composable
private fun RelativeTime.label(): String = when (this) {
    RelativeTime.Never -> stringResource(R.string.library_time_never)
    RelativeTime.JustNow -> stringResource(R.string.library_time_just_now)
    RelativeTime.Yesterday -> stringResource(R.string.library_time_yesterday)
    is RelativeTime.Minutes -> pluralStringResource(R.plurals.library_time_minutes_ago, count, count)
    is RelativeTime.Hours -> pluralStringResource(R.plurals.library_time_hours_ago, count, count)
    is RelativeTime.Days -> pluralStringResource(R.plurals.library_time_days_ago, count, count)
    is RelativeTime.Months -> pluralStringResource(R.plurals.library_time_months_ago, count, count)
    is RelativeTime.Years -> pluralStringResource(R.plurals.library_time_years_ago, count, count)
}
