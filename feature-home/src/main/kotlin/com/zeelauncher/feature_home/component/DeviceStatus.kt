package com.zeelauncher.feature_home.component

import android.content.Context
import android.os.BatteryManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalContext
import com.zeelauncher.core_common.constant.ONE_HUNDRED
import com.zeelauncher.core_common.constant.ONE_SECOND_MILLIS
import com.zeelauncher.core_common.constant.ZERO
import kotlinx.coroutines.delay
import java.time.LocalTime
import java.time.format.DateTimeFormatter

private const val CLOCK_PATTERN = "HH:mm"

internal data class DeviceStatus(val battery: Int, val time: String)

private val ClockFormat: DateTimeFormatter = DateTimeFormatter.ofPattern(CLOCK_PATTERN)

private fun readDeviceStatus(context: Context): DeviceStatus {
    val battery = (
        context.getSystemService(BatteryManager::class.java)
            ?.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY) ?: ZERO
        ).coerceIn(ZERO, ONE_HUNDRED)
    return DeviceStatus(battery, LocalTime.now().format(ClockFormat))
}

@Composable
internal fun rememberDeviceStatus(): DeviceStatus {
    val context = LocalContext.current
    val status by produceState(readDeviceStatus(context), context) {
        while (true) {
            value = readDeviceStatus(context)
            delay(ONE_SECOND_MILLIS)
        }
    }
    return status
}
