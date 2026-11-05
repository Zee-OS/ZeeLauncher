package com.dwiaziprasetya.zeelauncher

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.zeelauncher.core_designsystem.layout.ZeeCanvas
import com.zeelauncher.core_designsystem.theme.ZeeLauncherTheme
import com.zeelauncher.core_input.ZeeInputDispatcher
import com.zeelauncher.feature_home.screen.HomeRoute

class MainActivity : ComponentActivity() {

    private val input = ZeeInputDispatcher()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        hideSystemBars()
        setContent {
            ZeeLauncherTheme {
                ZeeCanvas {
                    HomeRoute(actions = input.actions)
                }
            }
        }
    }

    @SuppressLint("RestrictedApi")
    override fun dispatchKeyEvent(event: KeyEvent): Boolean =
        input.dispatch(event) || super.dispatchKeyEvent(event)

    private fun hideSystemBars() {
        WindowCompat.getInsetsController(window, window.decorView).apply {
            hide(WindowInsetsCompat.Type.systemBars())
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
}
