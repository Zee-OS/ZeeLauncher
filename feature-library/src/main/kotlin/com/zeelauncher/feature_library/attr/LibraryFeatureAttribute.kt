package com.zeelauncher.feature_library.attr

import com.zeelauncher.core_common.constant.TWO
import com.zeelauncher.core_designsystem.component.ZeeAppGridSpec
import com.zeelauncher.core_designsystem.theme.Dimen
import com.zeelauncher.core_designsystem.theme.TextDimen

internal object LibraryFeatureAttribute {

    object Import {
        const val VISIBLE_ROWS = 2
        const val GRID_COLUMNS = 4
        val GridSpec = ZeeAppGridSpec(
            columns = Import.GRID_COLUMNS,
            visibleRows = Import.VISIBLE_ROWS,
            tileSize = Dimen.Dp80,
            cellWidth = Dimen.Dp108,
            cellHeight = Dimen.Dp124,
            columnSpacing = Dimen.Dp20,
            rowSpacing = Dimen.Dp20,
            labelSpacing = Dimen.Dp10,
            labelSize = TextDimen.Sp14,
            labelLines = TWO,
            placeholderLabelSize = TextDimen.Sp20,
        )
    }

    object AppInfoDrawer {
        val IconSize = Dimen.Dp72
        val HeaderSpacing = Dimen.Dp18
        val SectionSpacing = Dimen.Dp40
        val InfoRowSpacing = Dimen.Dp20
        val OptionSpacing = Dimen.Dp12
    }

    object Footer {
        const val INFO_LABEL = "footer_info"
        const val APP_INFO_HINT_LABEL = "app_info_hint"
        val HintSpacing = Dimen.Dp22
    }

    object GameCarousel {
        val CardSmall = Dimen.Dp170
        val CardLarge = Dimen.Dp340
        val CardGap = Dimen.Dp30
        val SwipeStep = Dimen.Dp200
        val FlingVelocityPerStep = Dimen.Dp720
        const val MAX_FLING_STEPS = 5
    }

    object Screen {
        const val CAROUSEL_ANIMATION_LABEL = "carousel"
        const val LIBRARY_CHANGE_LABEL = "library_change"
    }

    object RelativeTime {
        const val DAYS_PER_MONTH = 30L
        const val DAYS_PER_YEAR = 365L
    }

    object UiState {
        const val IMPORT_ITEM_KEY = "import"
    }

    object ViewModel {
        const val EFFECT_BUFFER_CAPACITY = 8
    }
}
