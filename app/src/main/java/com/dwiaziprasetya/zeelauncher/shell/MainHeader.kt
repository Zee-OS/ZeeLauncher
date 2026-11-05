package com.dwiaziprasetya.zeelauncher.shell

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.zeelauncher.core_designsystem.component.ZeeLogo
import com.zeelauncher.core_designsystem.component.ZeeStatusBar
import com.zeelauncher.core_designsystem.component.ZeeTabRow

@Composable
internal fun MainHeader(
    selectedTab: MainTab,
    onTabClick: (MainTab) -> Unit,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val tabs = MainTab.entries
    val labels = tabs.map { stringResource(it.labelRes) }
    val status = rememberDeviceStatus()
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
            batteryPercent = status.battery,
            time = status.time,
            modifier = Modifier.align(Alignment.CenterEnd),
        )
    }
}
