package com.dwiaziprasetya.zeelauncher.shell

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.dwiaziprasetya.zeelauncher.R
import com.zeelauncher.core_common.constant.COLON
import com.zeelauncher.core_common.constant.ONE
import com.zeelauncher.core_common.constant.ONE_FLOAT
import com.zeelauncher.core_common.constant.ONE_HUNDRED_FLOAT
import com.zeelauncher.core_designsystem.component.ZeeButtonHint
import com.zeelauncher.core_designsystem.component.ZeeHintRow
import com.zeelauncher.core_designsystem.modifier.hardShadow
import com.zeelauncher.core_designsystem.theme.Dimen
import com.zeelauncher.core_designsystem.theme.TextDimen
import com.zeelauncher.core_designsystem.theme.ZeeDimens
import com.zeelauncher.core_designsystem.theme.ZeeTheme
import com.zeelauncher.core_designsystem.R as DesignR

private const val BATTERY_LOW_THRESHOLD = 20

private val DateSpacing = Dimen.Dp16
private val SectionSpacing = Dimen.Dp40
private val CardSpacing = Dimen.Dp20
private val CardHeight = Dimen.Dp150
private val CardPadding = Dimen.Dp20
private val CardLabelSpacing = Dimen.Dp8
private val SignalBarWidth = Dimen.Dp8
private val SignalBarSpacing = Dimen.Dp5
private val SignalBarStep = Dimen.Dp6
private val BatteryBarHeight = Dimen.Dp18

@Composable
internal fun ColumnScope.StatusDrawerContent(status: DeviceStatus) {
    val colors = ZeeTheme.colors
    Text(
        text = buildAnnotatedString {
            append(status.hours)
            withStyle(SpanStyle(color = colors.accent)) { append(COLON) }
            append(status.minutes)
        },
        style = ZeeTheme.typography.display.copy(fontSize = TextDimen.Sp84),
        color = colors.title,
        maxLines = ONE,
    )
    Spacer(Modifier.height(DateSpacing))
    Text(
        text = status.date,
        style = ZeeTheme.typography.label,
        color = colors.contentSecondary,
        maxLines = ONE,
    )
    Spacer(Modifier.height(SectionSpacing))
    Row(horizontalArrangement = Arrangement.spacedBy(CardSpacing)) {
        StatusCard(
            label = stringResource(R.string.status_network),
            value = stringResource(status.network.type.labelRes()),
        ) {
            SignalBars(level = status.signalLevel)
        }
        StatusCard(
            label = stringResource(if (status.isCharging) R.string.status_battery_charging else R.string.status_battery),
            value = stringResource(DesignR.string.zee_battery_percent, status.batteryPercent),
        ) {
            BatteryBar(percent = status.batteryPercent)
        }
    }
    Spacer(Modifier.weight(ONE_FLOAT))
    ZeeHintRow(
        listOf(
            ZeeButtonHint(stringResource(DesignR.string.zee_key_b), stringResource(R.string.status_hint_close)),
        ),
    )
}

@Composable
private fun RowScope.StatusCard(
    label: String,
    value: String,
    indicator: @Composable () -> Unit,
) {
    val colors = ZeeTheme.colors
    Column(
        modifier = Modifier
            .weight(ONE_FLOAT)
            .height(CardHeight)
            .hardShadow(ZeeDimens.ShadowMedium, colors.shadow)
            .background(colors.background)
            .border(ZeeDimens.BorderThin, colors.content)
            .padding(CardPadding),
    ) {
        Text(
            text = label,
            style = ZeeTheme.typography.badge,
            color = colors.contentSecondary,
            maxLines = ONE,
        )
        Spacer(Modifier.height(CardLabelSpacing))
        Text(
            text = value,
            style = ZeeTheme.typography.headline,
            color = colors.title,
            maxLines = ONE,
        )
        Spacer(Modifier.weight(ONE_FLOAT))
        indicator()
    }
}

@Composable
private fun SignalBars(level: Int) {
    val colors = ZeeTheme.colors
    Row(
        horizontalArrangement = Arrangement.spacedBy(SignalBarSpacing),
        verticalAlignment = Alignment.Bottom,
    ) {
        (ONE..MAX_SIGNAL_LEVEL).forEach { bar ->
            Box(
                Modifier
                    .size(SignalBarWidth, SignalBarStep * bar)
                    .background(if (bar <= level) colors.content else colors.shadow),
            )
        }
    }
}

@Composable
private fun BatteryBar(percent: Int) {
    val colors = ZeeTheme.colors
    Box(
        Modifier
            .fillMaxWidth()
            .height(BatteryBarHeight)
            .border(ZeeDimens.BorderThin, colors.content)
            .padding(ZeeDimens.BorderThin),
    ) {
        Box(
            Modifier
                .fillMaxHeight()
                .fillMaxWidth(percent / ONE_HUNDRED_FLOAT)
                .background(if (percent <= BATTERY_LOW_THRESHOLD) colors.accent else colors.success),
        )
    }
}

private fun NetworkType.labelRes(): Int = when (this) {
    NetworkType.Wifi -> R.string.status_network_wifi
    NetworkType.Cellular -> R.string.status_network_mobile
    NetworkType.Ethernet -> R.string.status_network_ethernet
    NetworkType.None -> R.string.status_network_offline
}
