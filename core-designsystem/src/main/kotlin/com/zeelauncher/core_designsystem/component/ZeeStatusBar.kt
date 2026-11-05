package com.zeelauncher.core_designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.zeelauncher.core_common.constant.ONE
import com.zeelauncher.core_designsystem.R
import com.zeelauncher.core_designsystem.icon.ZeeBatteryIcon
import com.zeelauncher.core_designsystem.icon.ZeeBellIcon
import com.zeelauncher.core_designsystem.icon.ZeeWifiIcon
import com.zeelauncher.core_designsystem.theme.Dimen
import com.zeelauncher.core_designsystem.theme.ZeeTheme

@Composable
fun ZeeStatusBar(
    batteryPercent: Int,
    time: String,
    modifier: Modifier = Modifier,
) {
    val style = ZeeTheme.typography.status
    val color = ZeeTheme.colors.content
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(Dimen.Dp20),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ZeeBellIcon()
        ZeeWifiIcon()
        Row(
            horizontalArrangement = Arrangement.spacedBy(Dimen.Dp8),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ZeeBatteryIcon(batteryPercent)
            Text(
                text = stringResource(R.string.zee_battery_percent, batteryPercent),
                style = style,
                color = color,
                maxLines = ONE,
            )
        }
        Text(text = time, style = style, color = color, maxLines = ONE)
    }
}
