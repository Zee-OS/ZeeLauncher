package com.zeelauncher.feature_settings.screen

import android.app.Activity
import android.app.role.RoleManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zeelauncher.core_common.constant.DASH
import com.zeelauncher.core_common.constant.ONE
import com.zeelauncher.core_common.constant.ONE_FLOAT
import com.zeelauncher.core_common.locale.AppLanguage
import com.zeelauncher.core_designsystem.R as DesignR
import com.zeelauncher.core_designsystem.component.ZeeButtonHint
import com.zeelauncher.core_designsystem.component.ZeeHintRow
import com.zeelauncher.core_designsystem.component.ZeeModal
import com.zeelauncher.core_designsystem.component.zeeBackHint
import com.zeelauncher.core_designsystem.overlay.ZeeOverlay
import com.zeelauncher.core_designsystem.overlay.isOverlayActive
import com.zeelauncher.core_designsystem.theme.ZeeDimens
import com.zeelauncher.core_designsystem.theme.ZeeTheme
import com.zeelauncher.core_designsystem.toast.LocalZeeToast
import com.zeelauncher.core_designsystem.toast.ZeeToastIcon
import com.zeelauncher.core_input.ObserveZeeActions
import com.zeelauncher.feature_settings.R
import com.zeelauncher.feature_settings.attr.SettingsFeatureAttribute
import com.zeelauncher.feature_settings.component.LicensesDialogContent
import com.zeelauncher.feature_settings.component.SettingsPanel
import com.zeelauncher.feature_settings.viewmodel.SettingsEffect
import com.zeelauncher.feature_settings.viewmodel.SettingsRow
import com.zeelauncher.feature_settings.viewmodel.SettingsUiState
import com.zeelauncher.feature_settings.viewmodel.SettingsViewModel

@Composable
internal fun SettingsRoute(
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val resources = LocalResources.current
    val toast = LocalZeeToast.current
    val roleRequest = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        viewModel.refresh()
    }

    ObserveZeeActions(viewModel::onAction)

    LifecycleResumeEffect(viewModel) {
        viewModel.refresh()
        onPauseOrDispose { }
    }

    LaunchedEffect(viewModel) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is SettingsEffect.OpenDefaultLauncher -> {
                    val roleManager = context.getSystemService(RoleManager::class.java)
                    if (!effect.isDefault && roleManager.isRoleAvailable(RoleManager.ROLE_HOME)) {
                        roleRequest.launch(roleManager.createRequestRoleIntent(RoleManager.ROLE_HOME))
                    } else {
                        context.openHomeSettings()
                    }
                }
                SettingsEffect.Recreate -> context.findActivity()?.recreate()
                SettingsEffect.OpenSourceCode -> {
                    if (!context.openUrl(resources.getString(R.string.settings_source_url))) {
                        toast.show(resources.getString(R.string.settings_toast_no_browser), ZeeToastIcon.Close)
                    }
                }
            }
        }
    }

    SettingsScreen(
        uiState = uiState,
        onRowClick = viewModel::onRowClick,
        onLanguageClick = viewModel::onLanguageClick,
    )

    ZeeOverlay(visible = uiState.isLicensesVisible) {
        val isActive = isOverlayActive
        ObserveZeeActions { action -> isActive && viewModel.onLicensesAction(action) }
        ZeeModal(onDismiss = viewModel::closeLicenses) {
            LicensesDialogContent(onOpenClick = viewModel::openSourceCode)
        }
    }
}

@Composable
internal fun SettingsScreen(
    uiState: SettingsUiState,
    onRowClick: (SettingsRow) -> Unit,
    onLanguageClick: (AppLanguage) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .weight(ONE_FLOAT)
                .fillMaxWidth()
                .padding(horizontal = ZeeDimens.ScreenPaddingHorizontal),
            contentAlignment = Alignment.Center,
        ) {
            SettingsPanel(
                state = uiState,
                onRowClick = onRowClick,
                onLanguageClick = onLanguageClick,
                modifier = Modifier.fillMaxWidth(SettingsFeatureAttribute.Screen.PANEL_WIDTH_FRACTION),
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = ZeeDimens.ScreenPaddingHorizontal),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.settings_footer_version, uiState.versionName ?: DASH).uppercase(),
                style = ZeeTheme.typography.label,
                color = ZeeTheme.colors.contentSecondary,
                maxLines = ONE,
                modifier = Modifier.weight(ONE_FLOAT),
            )
            ZeeHintRow(
                listOf(
                    ZeeButtonHint(stringResource(DesignR.string.zee_key_a), stringResource(R.string.settings_hint_select)),
                    zeeBackHint(),
                ),
            )
        }
    }
}

private fun Context.openHomeSettings() {
    try {
        startActivity(Intent(Settings.ACTION_HOME_SETTINGS))
    } catch (exception: ActivityNotFoundException) {
        startActivity(Intent(Settings.ACTION_SETTINGS))
    }
}

private fun Context.openUrl(url: String): Boolean = try {
    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    true
} catch (exception: ActivityNotFoundException) {
    false
}

private tailrec fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}
