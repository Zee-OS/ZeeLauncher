package com.dwiaziprasetya.zeelauncher

import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.dwiaziprasetya.zeelauncher.navigation.ZeeRootNavHost
import com.zeelauncher.core_designsystem.layout.ZeeCanvas
import com.zeelauncher.core_designsystem.theme.ZeeLauncherTheme
import com.zeelauncher.core_input.LocalZeeInput
import com.zeelauncher.core_input.ZeeInputDispatcher

class MainActivity : ComponentActivity() {

    private val input = ZeeInputDispatcher()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        hideSystemBars()
        setContent {
            CompositionLocalProvider(LocalZeeInput provides input) {
                ZeeLauncherTheme {
                    ZeeCanvas {
                        ZeeRootNavHost()
                    }
                }
            }
        }
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean =
        input.dispatch(event) || super.dispatchKeyEvent(event)

    private fun hideSystemBars() {
        WindowCompat.getInsetsController(window, window.decorView).apply {
            hide(WindowInsetsCompat.Type.systemBars())
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
}
