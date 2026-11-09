package com.dwiaziprasetya.zeelauncher.shell

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Handler
import android.os.Looper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.dwiaziprasetya.zeelauncher.attr.AppAttribute
import com.zeelauncher.core_common.constant.FOUR
import com.zeelauncher.core_common.constant.ONE
import com.zeelauncher.core_common.constant.THREE
import com.zeelauncher.core_common.constant.TWO
import com.zeelauncher.core_common.constant.ZERO

internal enum class NetworkType { Wifi, Cellular, Ethernet, None }

@Immutable
internal data class NetworkStatus(
    val type: NetworkType = NetworkType.None,
    val wifiLevel: Int = ZERO,
)

@Composable
internal fun rememberNetworkStatus(): State<NetworkStatus> {
    val context = LocalContext.current
    val connectivityManager = remember(context) { context.getSystemService(ConnectivityManager::class.java) }
    val status = remember(connectivityManager) { mutableStateOf(connectivityManager.currentNetworkStatus()) }

    DisposableEffect(connectivityManager) {
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onCapabilitiesChanged(network: Network, capabilities: NetworkCapabilities) {
                status.value = capabilities.toNetworkStatus()
            }

            override fun onLost(network: Network) {
                status.value = NetworkStatus()
            }
        }
        connectivityManager.registerDefaultNetworkCallback(callback, Handler(Looper.getMainLooper()))
        onDispose { connectivityManager.unregisterNetworkCallback(callback) }
    }
    return status
}

private fun ConnectivityManager.currentNetworkStatus(): NetworkStatus =
    getNetworkCapabilities(activeNetwork)?.toNetworkStatus() ?: NetworkStatus()

private fun NetworkCapabilities.toNetworkStatus(): NetworkStatus = when {
    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> NetworkStatus(NetworkType.Wifi, wifiLevel())
    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> NetworkStatus(NetworkType.Cellular)
    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> NetworkStatus(NetworkType.Ethernet)
    else -> NetworkStatus()
}

private fun NetworkCapabilities.wifiLevel(): Int {
    val rssi = signalStrength
    return when {
        rssi == NetworkCapabilities.SIGNAL_STRENGTH_UNSPECIFIED -> AppAttribute.Network.MAX_SIGNAL_LEVEL
        rssi >= AppAttribute.Network.RSSI_EXCELLENT -> FOUR
        rssi >= AppAttribute.Network.RSSI_GOOD -> THREE
        rssi >= AppAttribute.Network.RSSI_FAIR -> TWO
        else -> ONE
    }
}
