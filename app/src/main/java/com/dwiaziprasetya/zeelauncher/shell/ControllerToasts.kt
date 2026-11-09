package com.dwiaziprasetya.zeelauncher.shell

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalResources
import com.dwiaziprasetya.zeelauncher.R
import com.zeelauncher.core_designsystem.toast.LocalZeeToast
import com.zeelauncher.core_designsystem.toast.ZeeToastIcon
import com.zeelauncher.core_input.ObserveControllerConnections

@Composable
internal fun ControllerToasts() {
    val resources = LocalResources.current
    val toast = LocalZeeToast.current
    ObserveControllerConnections(
        onConnected = {
            toast.show(resources.getString(R.string.shell_toast_controller_connected), ZeeToastIcon.Controller)
        },
        onDisconnected = {
            toast.show(resources.getString(R.string.shell_toast_controller_disconnected), ZeeToastIcon.ControllerOff)
        },
    )
}
