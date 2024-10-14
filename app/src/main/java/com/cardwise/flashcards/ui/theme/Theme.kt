package com.cardwise.flashcards.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF81D4FA),        // Light Blue 200
    onPrimary = Color(0xFF000000),      // Black
    primaryContainer = Color(0xFF0288D1),  // Light Blue 700
    onPrimaryContainer = Color(0xFFFFFFFF),  // White
    secondary = Color(0xFFB39DDB),      // Deep Purple 200
    onSecondary = Color(0xFF000000),    // Black
    secondaryContainer = Color(0xFF673AB7),  // Deep Purple 500
    onSecondaryContainer = Color(0xFFFFFFFF),  // White
    tertiary = Color(0xFFFFCC80),       // Orange 200
    onTertiary = Color(0xFF000000),     // Black
    tertiaryContainer = Color(0xFFF57C00),  // Orange 700
    onTertiaryContainer = Color(0xFFFFFFFF),  // White
    error = Color(0xFFCF6679),          // Red-ish pink
    onError = Color(0xFF000000),        // Black
    errorContainer = Color(0xFFB00020),  // Dark red
    onErrorContainer = Color(0xFFFFFFFF),  // White
    background = Color(0xFF121212),     // Dark gray
    onBackground = Color(0xFFFFFFFF),   // White
    surface = Color(0xFF121212),        // Dark gray
    onSurface = Color(0xFFFFFFFF)       // White
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF03A9F4),        // Light Blue 500
    onPrimary = Color(0xFFFFFFFF),      // White
    primaryContainer = Color(0xFFB3E5FC),  // Light Blue 100
    onPrimaryContainer = Color(0xFF000000),  // Black
    secondary = Color(0xFF673AB7),      // Deep Purple 500
    onSecondary = Color(0xFFFFFFFF),    // White
    secondaryContainer = Color(0xFFD1C4E9),  // Deep Purple 100
    onSecondaryContainer = Color(0xFF000000),  // Black
    tertiary = Color(0xFFFF9800),       // Orange 500
    onTertiary = Color(0xFF000000),     // Black
    tertiaryContainer = Color(0xFFFFE0B2),  // Orange 100
    onTertiaryContainer = Color(0xFF000000),  // Black
    error = Color(0xFFB00020),          // Dark red
    onError = Color(0xFFFFFFFF),        // White
    errorContainer = Color(0xFFFDE7E9),  // Light pink
    onErrorContainer = Color(0xFF000000),  // Black
    background = Color(0xFFFAFAFA),     // Very light gray
    onBackground = Color(0xFF000000),   // Black
    surface = Color(0xFFFFFFFF),        // White
    onSurface = Color(0xFF000000)       // Black
)

@Composable
fun CardWiseTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}