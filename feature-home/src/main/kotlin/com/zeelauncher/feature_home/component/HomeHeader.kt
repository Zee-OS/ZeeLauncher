package com.zeelauncher.feature_home.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.zeelauncher.core_common.constant.ONE
import com.zeelauncher.core_common.constant.ZERO
import com.zeelauncher.core_designsystem.component.ZeeLogo
import com.zeelauncher.core_designsystem.component.ZeeStatusBar
import com.zeelauncher.core_designsystem.component.ZeeTabRow
import com.zeelauncher.feature_home.viewmodel.HomeTab

@Composable
internal fun HomeHeader(
    selectedTab: HomeTab,
    onTabClick: (HomeTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    val tabs = HomeTab.entries
    val labels = tabs.map { stringResource(it.labelRes) }
    val status = rememberDeviceStatus()
    Box(modifier.fillMaxWidth()) {
        ZeeLogo(Modifier.align(Alignment.CenterStart))
        ZeeTabRow(
            labels = labels,
            selectedIndex = selectedTab.ordinal,
            onTabClick = { onTabClick(tabs[it]) },
            onPrevious = { onTabClick(tabs[(selectedTab.ordinal - ONE).coerceAtLeast(ZERO)]) },
            onNext = { onTabClick(tabs[(selectedTab.ordinal + ONE).coerceAtMost(tabs.lastIndex)]) },
            modifier = Modifier.align(Alignment.Center),
        )
        ZeeStatusBar(
            batteryPercent = status.battery,
            time = status.time,
            modifier = Modifier.align(Alignment.CenterEnd),
        )
    }
}
