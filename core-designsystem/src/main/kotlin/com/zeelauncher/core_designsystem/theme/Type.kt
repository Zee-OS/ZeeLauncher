package com.zeelauncher.core_designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.zeelauncher.core_designsystem.R
import com.zeelauncher.core_designsystem.attr.DesignSystemAttribute

val Rajdhani = FontFamily(
    Font(R.font.rajdhani_medium, FontWeight.Medium),
    Font(R.font.rajdhani_semibold, FontWeight.SemiBold),
    Font(R.font.rajdhani_bold, FontWeight.Bold),
)

@Immutable
data class ZeeTypography(
    val display: TextStyle,
    val logo: TextStyle,
    val headline: TextStyle,
    val button: TextStyle,
    val buttonSmall: TextStyle,
    val status: TextStyle,
    val body: TextStyle,
    val label: TextStyle,
    val hint: TextStyle,
    val cardLabel: TextStyle,
    val badge: TextStyle,
)

val ZeeDefaultTypography = ZeeTypography(
    display = DesignSystemAttribute.Type.Base.copy(
        fontSize = TextDimen.Sp34,
        fontWeight = FontWeight.Bold,
        lineHeight = TextDimen.Em1_1,
    ),
    logo = DesignSystemAttribute.Type.Base.copy(
        fontSize = TextDimen.Sp28,
        fontWeight = FontWeight.Bold,
    ),
    headline = DesignSystemAttribute.Type.Base.copy(
        fontSize = TextDimen.Sp26,
        fontWeight = FontWeight.Bold,
        letterSpacing = TextDimen.Em0_1,
    ),
    button = DesignSystemAttribute.Type.Base.copy(
        fontSize = TextDimen.Sp18,
        fontWeight = FontWeight.Bold,
        letterSpacing = TextDimen.Em0_12,
    ),
    buttonSmall = DesignSystemAttribute.Type.Base.copy(
        fontSize = TextDimen.Sp16,
        fontWeight = FontWeight.Bold,
        letterSpacing = TextDimen.Em0_04,
    ),
    status = DesignSystemAttribute.Type.Base.copy(
        fontSize = TextDimen.Sp18,
        fontWeight = FontWeight.Bold,
        letterSpacing = TextDimen.Em0_04,
    ),
    body = DesignSystemAttribute.Type.Base.copy(
        fontSize = TextDimen.Sp17,
        fontWeight = FontWeight.Medium,
    ),
    label = DesignSystemAttribute.Type.Base.copy(
        fontSize = TextDimen.Sp16,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = TextDimen.Em0_12,
    ),
    hint = DesignSystemAttribute.Type.Base.copy(
        fontSize = TextDimen.Sp16,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = TextDimen.Em0_04,
    ),
    cardLabel = DesignSystemAttribute.Type.Base.copy(
        fontSize = TextDimen.Sp16,
        fontWeight = FontWeight.Bold,
        letterSpacing = TextDimen.Em0_1,
    ),
    badge = DesignSystemAttribute.Type.Base.copy(
        fontSize = TextDimen.Sp15,
        fontWeight = FontWeight.Bold,
    ),
)

val LocalZeeTypography = staticCompositionLocalOf { ZeeDefaultTypography }
