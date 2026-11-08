package com.dwiaziprasetya.zeelauncher.shell

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.telephony.TelephonyManager
import android.text.format.DateFormat
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalContext
import com.zeelauncher.core_common.constant.COLON
import com.zeelauncher.core_common.constant.MINUS_ONE
import com.zeelauncher.core_common.constant.ONE_HUNDRED
import com.zeelauncher.core_common.constant.ONE_SECOND_MILLIS
import com.zeelauncher.core_common.constant.ZERO
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private const val HOURS_24_PATTERN = "HH"
private const val HOURS_12_PATTERN = "h"
private const val MINUTES_PATTERN = "mm"
private const val DATE_PATTERN = "EEEE, d MMMM"

@Immutable
internal data class DeviceStatus(
    val batteryPercent: Int,
    val isCharging: Boolean,
    val hours: String,
    val minutes: String,
    val date: String,
    val network: NetworkStatus,
    val cellularLevel: Int,
) {
    val time: String get() = hours + COLON + minutes

    val signalLevel: Int
        get() = when (network.type) {
            NetworkType.Wifi -> network.wifiLevel
            NetworkType.Cellular -> cellularLevel
            NetworkType.Ethernet -> MAX_SIGNAL_LEVEL
            NetworkType.None -> ZERO
        }
}

private data class ClockBattery(
    val batteryPercent: Int,
    val isCharging: Boolean,
    val hours: String,
    val minutes: String,
    val date: String,
    val cellularLevel: Int,
)

private fun readClockBattery(context: Context): ClockBattery {
    val battery = (
        context.getSystemService(BatteryManager::class.java)
            ?.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY) ?: ZERO
        ).coerceIn(ZERO, ONE_HUNDRED)
    val batteryStatus = context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        ?.getIntExtra(BatteryManager.EXTRA_STATUS, MINUS_ONE) ?: MINUS_ONE
    val isCharging = batteryStatus == BatteryManager.BATTERY_STATUS_CHARGING ||
        batteryStatus == BatteryManager.BATTERY_STATUS_FULL
    val locale = context.resources.configuration.locales[ZERO]
    val hoursPattern = if (DateFormat.is24HourFormat(context)) HOURS_24_PATTERN else HOURS_12_PATTERN
    val now = LocalDateTime.now()
    return ClockBattery(
        batteryPercent = battery,
        isCharging = isCharging,
        hours = now.format(DateTimeFormatter.ofPattern(hoursPattern, locale)),
        minutes = now.format(DateTimeFormatter.ofPattern(MINUTES_PATTERN, locale)),
        date = now.format(DateTimeFormatter.ofPattern(DATE_PATTERN, locale)).uppercase(locale),
        cellularLevel = context.cellularLevel(),
    )
}

private fun Context.cellularLevel(): Int = try {
    getSystemService(TelephonyManager::class.java)?.signalStrength?.level?.coerceIn(ZERO, MAX_SIGNAL_LEVEL) ?: ZERO
} catch (exception: SecurityException) {
    ZERO
}

@Composable
internal fun rememberDeviceStatus(): DeviceStatus {
    val context = LocalContext.current
    val network by rememberNetworkStatus()
    val clockBattery by produceState(readClockBattery(context), context) {
        while (true) {
            value = readClockBattery(context)
            delay(ONE_SECOND_MILLIS)
        }
    }
    return DeviceStatus(
        batteryPercent = clockBattery.batteryPercent,
        isCharging = clockBattery.isCharging,
        hours = clockBattery.hours,
        minutes = clockBattery.minutes,
        date = clockBattery.date,
        network = network,
        cellularLevel = clockBattery.cellularLevel,
    )
}
