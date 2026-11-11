package com.dwiaziprasetya.zeelauncher

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.view.KeyEvent
import android.view.animation.PathInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.dwiaziprasetya.zeelauncher.attr.AppAttribute
import com.dwiaziprasetya.zeelauncher.navigation.ZeeRootNavHost
import com.dwiaziprasetya.zeelauncher.shell.ControllerToasts
import com.dwiaziprasetya.zeelauncher.sound.attachInputSounds
import com.zeelauncher.core_common.constant.ZERO_FLOAT
import com.zeelauncher.core_common.home.HomeRequests
import com.zeelauncher.core_common.locale.AppLocale
import com.zeelauncher.core_common.startup.AppReadiness
import com.zeelauncher.core_data.usecase.ObserveSoundEnabledUseCase
import com.zeelauncher.core_designsystem.layout.ZeeCanvas
import com.zeelauncher.core_designsystem.motion.ZeeMotion
import com.zeelauncher.core_designsystem.overlay.LocalZeeOverlayHost
import com.zeelauncher.core_designsystem.overlay.ZeeOverlayHost
import com.zeelauncher.core_designsystem.overlay.ZeeOverlayHostState
import com.zeelauncher.core_designsystem.sound.LocalZeeSound
import com.zeelauncher.core_designsystem.theme.ZeeLauncherTheme
import com.zeelauncher.core_designsystem.toast.LocalZeeToast
import com.zeelauncher.core_designsystem.toast.ZeeToastHost
import com.zeelauncher.core_designsystem.toast.ZeeToastHostState
import com.zeelauncher.core_input.LocalZeeInput
import com.zeelauncher.core_input.ZeeAction
import com.zeelauncher.core_input.ZeeInputDispatcher
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.zeelauncher.core_data.usecase.IsDefaultLauncherUseCase

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var appReadiness: AppReadiness

    @Inject
    lateinit var homeRequests: HomeRequests

    @Inject
    lateinit var observeSoundEnabledUseCase: ObserveSoundEnabledUseCase

    @Inject
    lateinit var isDefaultLauncherUseCase: IsDefaultLauncherUseCase

    private val input = ZeeInputDispatcher()

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(AppLocale.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        holdSplashUntilReady(splashScreen)
        enableEdgeToEdge()
        hideSystemBars()
        onBackPressedDispatcher.addCallback(this) {
            if (!input.dispatch(ZeeAction.Back) && !isDefaultLauncherUseCase()) moveTaskToBack(true)
        }
        val sounds = attachInputSounds(input, observeSoundEnabledUseCase())
        setContent {
            val overlayHost = remember { ZeeOverlayHostState() }
            val toastHost = remember { ZeeToastHostState() }
            CompositionLocalProvider(
                LocalZeeInput provides input,
                LocalZeeOverlayHost provides overlayHost,
                LocalZeeToast provides toastHost,
                LocalZeeSound provides sounds,
            ) {
                ZeeLauncherTheme {
                    ZeeCanvas {
                        Box {
                            ZeeRootNavHost(homeRequests = homeRequests.requests)
                            ZeeOverlayHost(overlayHost)
                            ZeeToastHost(toastHost)
                        }
                        ControllerToasts()
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (intent.hasCategory(Intent.CATEGORY_HOME)) homeRequests.request()
    }

    @SuppressLint("RestrictedApi")
    override fun dispatchKeyEvent(event: KeyEvent): Boolean =
        input.dispatch(event) || super.dispatchKeyEvent(event)

    private fun holdSplashUntilReady(splashScreen: SplashScreen) {
        val startedAt = SystemClock.uptimeMillis()
        splashScreen.setKeepOnScreenCondition {
            val elapsed = SystemClock.uptimeMillis() - startedAt
            !appReadiness.isReady.value && elapsed < AppAttribute.MainActivity.SPLASH_TIMEOUT_MILLIS
        }
        splashScreen.setOnExitAnimationListener { provider ->
            provider.view.animate()
                .alpha(ZERO_FLOAT)
                .setDuration(ZeeMotion.DURATION_MILLIS.toLong())
                .setInterpolator(
                    PathInterpolator(ZeeMotion.EASING_X1, ZeeMotion.EASING_Y1, ZeeMotion.EASING_X2, ZeeMotion.EASING_Y2),
                )
                .withEndAction { provider.remove() }
                .start()
        }
    }

    private fun hideSystemBars() {
        WindowCompat.getInsetsController(window, window.decorView).apply {
            hide(WindowInsetsCompat.Type.systemBars())
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    override fun onStop() {
        super.onStop()
        if (intent.hasCategory(Intent.CATEGORY_HOME) && !isDefaultLauncherUseCase()) finishAndRemoveTask()
    }
}
