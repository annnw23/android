package com.celestial.viewport.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val CelestialColorScheme = darkColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = OnPrimaryContainer,
    secondary = Secondary,
    onSecondary = OnSecondary,
    secondaryContainer = SecondaryContainer,
    tertiary = Tertiary,
    onTertiary = OnTertiary,
    tertiaryContainer = TertiaryContainer,
    error = Error,
    onError = OnError,
    errorContainer = ErrorContainer,
    background = Background,
    onBackground = OnBackground,
    surface = Surface,
    onSurface = OnSurface,
    onSurfaceVariant = OnSurfaceVariant,
    surfaceVariant = SurfaceVariant,
    outline = Outline,
    outlineVariant = OutlineVariant,
    surfaceTint = SurfaceTint,
    inverseSurface = OnSurface,
    inverseOnSurface = OnSurfaceVariant,
    inversePrimary = OnPrimary,
)

@Composable
fun CelestialTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = CelestialColorScheme,
        typography = CelestialTypography,
        content = content
    )
}
