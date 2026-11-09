package com.zeelauncher.feature_library.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.zeelauncher.core_common.constant.ONE
import com.zeelauncher.core_designsystem.R as DesignR
import com.zeelauncher.core_designsystem.component.ZeeAppGrid
import com.zeelauncher.core_designsystem.component.ZeeButtonHint
import com.zeelauncher.core_designsystem.component.ZeeHintRow
import com.zeelauncher.core_designsystem.theme.Dimen
import com.zeelauncher.core_designsystem.theme.ZeeTheme
import com.zeelauncher.feature_library.R
import com.zeelauncher.feature_library.attr.LibraryFeatureAttribute
import com.zeelauncher.feature_library.viewmodel.ImportUiState

@Composable
internal fun ImportDialogContent(
    state: ImportUiState,
    onTileClick: (Int) -> Unit,
) {
    Text(
        text = stringResource(R.string.library_import_title),
        style = ZeeTheme.typography.headline,
        color = ZeeTheme.colors.title,
        maxLines = ONE,
    )
    Spacer(Modifier.height(Dimen.Dp30))
    Box(
        modifier = Modifier.size(
            LibraryFeatureAttribute.Import.GridSpec.width,
            LibraryFeatureAttribute.Import.GridSpec.height,
        ),
        contentAlignment = Alignment.Center,
    ) {
        when {
            state.isLoading -> Unit
            state.apps.isEmpty() -> Text(
                text = stringResource(R.string.library_import_empty),
                style = ZeeTheme.typography.label,
                color = ZeeTheme.colors.contentSecondary,
                textAlign = TextAlign.Center,
            )
            else -> ZeeAppGrid(
                apps = state.apps,
                selectedIndex = state.selectedIndex,
                spec = LibraryFeatureAttribute.Import.GridSpec,
                onTileClick = onTileClick,
            )
        }
    }
    Spacer(Modifier.height(Dimen.Dp30))
    ZeeHintRow(
        listOf(
            ZeeButtonHint(stringResource(DesignR.string.zee_key_a), stringResource(R.string.library_hint_import)),
            ZeeButtonHint(stringResource(DesignR.string.zee_key_b), stringResource(R.string.library_hint_cancel)),
        ),
    )
}
