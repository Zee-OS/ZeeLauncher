package com.zeelauncher.feature_apps.attr

import com.zeelauncher.core_common.constant.ONE
import com.zeelauncher.core_designsystem.component.ZeeAppGridSpec
import com.zeelauncher.core_designsystem.theme.Dimen
import com.zeelauncher.core_designsystem.theme.TextDimen
import com.zeelauncher.core_designsystem.theme.ZeeDimens

internal object AppsFeatureAttribute {

    object Screen {
        const val CONTENT_LABEL = "apps_content"
    }

    object Grid {
        const val VISIBLE_ROWS = 3
        const val COLUMNS = 6
        val Spec = ZeeAppGridSpec(
            columns = Grid.COLUMNS,
            visibleRows = Grid.VISIBLE_ROWS,
            tileSize = Dimen.Dp104,
            cellWidth = Dimen.Dp150,
            cellHeight = Dimen.Dp140,
            columnSpacing = Dimen.Dp16,
            rowSpacing = Dimen.Dp22,
            labelSpacing = Dimen.Dp12,
            labelSize = TextDimen.Sp16,
            labelLines = ONE,
            placeholderLabelSize = TextDimen.Sp26,
            tileTopInset = -ZeeDimens.FocusLift,
        )
    }

    object ViewModel {
        const val EFFECT_BUFFER_CAPACITY = 4
    }
}
