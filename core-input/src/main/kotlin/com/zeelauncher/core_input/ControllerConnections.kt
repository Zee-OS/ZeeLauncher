package com.zeelauncher.core_input

import android.content.Context
import android.hardware.input.InputManager
import android.os.Handler
import android.os.Looper
import android.view.InputDevice
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalContext

@Composable
fun ObserveControllerConnections(
    onConnected: () -> Unit,
    onDisconnected: () -> Unit,
) {
    val context = LocalContext.current
    val currentOnConnected by rememberUpdatedState(onConnected)
    val currentOnDisconnected by rememberUpdatedState(onDisconnected)

    DisposableEffect(context) {
        val inputManager = context.getSystemService(Context.INPUT_SERVICE) as InputManager
        val connected = inputManager.inputDeviceIds.filter { it.isGameController() }.toMutableSet()
        val listener = object : InputManager.InputDeviceListener {
            override fun onInputDeviceAdded(deviceId: Int) {
                if (deviceId.isGameController() && connected.add(deviceId)) currentOnConnected()
            }

            override fun onInputDeviceRemoved(deviceId: Int) {
                if (connected.remove(deviceId)) currentOnDisconnected()
            }

            override fun onInputDeviceChanged(deviceId: Int) = Unit
        }
        inputManager.registerInputDeviceListener(listener, Handler(Looper.getMainLooper()))
        onDispose { inputManager.unregisterInputDeviceListener(listener) }
    }
}

private fun Int.isGameController(): Boolean {
    val sources = InputDevice.getDevice(this)?.sources ?: return false
    return (sources and InputDevice.SOURCE_GAMEPAD) == InputDevice.SOURCE_GAMEPAD ||
        (sources and InputDevice.SOURCE_JOYSTICK) == InputDevice.SOURCE_JOYSTICK
}
