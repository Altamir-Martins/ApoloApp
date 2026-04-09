package com.apolo.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF667eea),
    secondary = Color(0xFFFDD835),
    tertiary = Color(0xFFE53935)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF667eea),
    secondary = Color(0xFFFDD835),
    tertiary = Color(0xFFE53935)
)

@Composable
fun ApoloTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}