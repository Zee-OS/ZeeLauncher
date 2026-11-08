package com.dwiaziprasetya.zeelauncher.shell

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.zeelauncher.core_designsystem.component.ZeeLogo
import com.zeelauncher.core_designsystem.component.ZeeNetworkIndicator
import com.zeelauncher.core_designsystem.component.ZeeStatusBar
import com.zeelauncher.core_designsystem.component.ZeeTabRow
import com.zeelauncher.core_designsystem.modifier.noRippleClickable

@Composable
internal fun MainHeader(
    selectedTab: MainTab,
    onTabClick: (MainTab) -> Unit,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    status: DeviceStatus,
    onStatusClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val tabs = MainTab.entries
    val labels = tabs.map { stringResource(it.labelRes) }
    Box(modifier.fillMaxWidth()) {
        ZeeLogo(Modifier.align(Alignment.CenterStart))
        ZeeTabRow(
            labels = labels,
            selectedIndex = selectedTab.ordinal,
            onTabClick = { onTabClick(tabs[it]) },
            onPrevious = onPrevious,
            onNext = onNext,
            modifier = Modifier.align(Alignment.Center),
        )
        ZeeStatusBar(
            batteryPercent = status.batteryPercent,
            network = status.network.type.toIndicator(),
            signalLevel = status.signalLevel,
            time = status.time,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .noRippleClickable(onStatusClick),
        )
    }
}

private fun NetworkType.toIndicator(): ZeeNetworkIndicator = when (this) {
    NetworkType.Wifi -> ZeeNetworkIndicator.Wifi
    NetworkType.Cellular, NetworkType.Ethernet -> ZeeNetworkIndicator.Signal
    NetworkType.None -> ZeeNetworkIndicator.Offline
}
