package com.spaceexplorer.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val SpaceColorScheme = darkColorScheme(
    primary = CyanAccent,
    onPrimary = Color(0xFF000000),
    secondary = OrangeAccent,
    onSecondary = Color(0xFF000000),
    background = DarkBackground,
    onBackground = PrimaryText,
    surface = SurfaceColor,
    onSurface = PrimaryText,
    surfaceVariant = CardBackground,
    onSurfaceVariant = SecondaryText,
    tertiary = OrangeAccent,
    outline = SecondaryText
)

@Composable
fun SpaceExplorerTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = SpaceColorScheme,
        content = content
    )
}
