package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Immutable
data class ForensicSemanticColors(
    val lucidityAccent: Color,
    val scrimOverlay: Color,
    val cardBorder: Color,
    val quoteBackground: Color
)

val LocalForensicColors = staticCompositionLocalOf {
    ForensicSemanticColors(
        lucidityAccent = EmeraldLucidityDark,
        scrimOverlay = Color(0xCC0D0F14),
        cardBorder = ObsidianOutline,
        quoteBackground = ObsidianSurfaceHigh
    )
}

private val DossierDarkColorScheme = darkColorScheme(
    primary = CrimsonSignal,
    onPrimary = OnCrimsonSignal,
    primaryContainer = CrimsonContainerDark,
    onPrimaryContainer = OnCrimsonContainerDark,
    secondary = AmberHighlightDark,
    onSecondary = OnAmberHighlightDark,
    secondaryContainer = AmberContainerDark,
    onSecondaryContainer = OnAmberContainerDark,
    tertiary = SteelForensicDark,
    onTertiary = OnSteelForensicDark,
    tertiaryContainer = SteelContainerDark,
    onTertiaryContainer = OnSteelContainerDark,
    background = ObsidianBackground,
    onBackground = ArchivalCreamText,
    surface = ObsidianSurface,
    onSurface = ArchivalCreamText,
    surfaceVariant = ObsidianSurfaceHigh,
    onSurfaceVariant = ArchivalMutedText,
    surfaceContainerLow = ObsidianSurface,
    surfaceContainer = ObsidianSurfaceHigh,
    surfaceContainerHigh = ObsidianSurfaceHighest,
    outline = ObsidianOutline,
    outlineVariant = ObsidianOutlineVariant
)

private val DossierLightColorScheme = lightColorScheme(
    primary = CrimsonEditorial,
    onPrimary = OnCrimsonEditorial,
    primaryContainer = CrimsonContainerLight,
    onPrimaryContainer = OnCrimsonContainerLight,
    secondary = AmberEditorial,
    onSecondary = OnAmberEditorial,
    secondaryContainer = AmberContainerLight,
    onSecondaryContainer = OnAmberContainerLight,
    tertiary = SteelEditorial,
    onTertiary = OnSteelEditorial,
    tertiaryContainer = SteelContainerLight,
    onTertiaryContainer = OnSteelContainerLight,
    background = ParchmentBackground,
    onBackground = InkText,
    surface = ParchmentSurface,
    onSurface = InkText,
    surfaceVariant = ParchmentSurfaceHigh,
    onSurfaceVariant = InkMutedText,
    surfaceContainerLow = ParchmentSurface,
    surfaceContainer = ParchmentSurfaceHigh,
    surfaceContainerHigh = ParchmentSurfaceHighest,
    outline = ParchmentOutline,
    outlineVariant = ParchmentOutlineVariant
)

val DossierShapes = Shapes(
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(14.dp),
    large = RoundedCornerShape(20.dp),
    extraLarge = RoundedCornerShape(26.dp)
)

@Composable
fun DossierTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DossierDarkColorScheme else DossierLightColorScheme
    val forensicColors = if (darkTheme) {
        ForensicSemanticColors(
            lucidityAccent = EmeraldLucidityDark,
            scrimOverlay = Color(0xD90E1015),
            cardBorder = ObsidianOutline,
            quoteBackground = ObsidianSurfaceHigh
        )
    } else {
        ForensicSemanticColors(
            lucidityAccent = EmeraldLucidityLight,
            scrimOverlay = Color(0xCC191410),
            cardBorder = ParchmentOutline,
            quoteBackground = ParchmentSurfaceHigh
        )
    }

    CompositionLocalProvider(LocalForensicColors provides forensicColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = DossierTypography,
            shapes = DossierShapes,
            content = content
        )
    }
}
