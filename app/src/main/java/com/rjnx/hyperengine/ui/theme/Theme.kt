package com.rjnx.hyperengine.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFFF00FF),
    primaryContainer = Color(0xFFFF3366),
    secondary = Color(0xFF00FFFF),
    secondaryContainer = Color(0xFF00CCCC),
    tertiary = Color(0xFFFF6600),
    background = Color(0xFF0A0A0F),
    surface = Color(0xFF1A1A25),
    surfaceVariant = Color(0xFF252535),
    surfaceContainer = Color(0xFF303040),
    onPrimary = Color(0xFF000000),
    onPrimaryContainer = Color(0xFFFFFFFF),
    onSecondary = Color(0xFF000000),
    onSecondaryContainer = Color(0xFFFFFFFF),
    onSurface = Color(0xFFFFFFFF),
    onSurfaceVariant = Color(0xFFB0B0C0),
    onBackground = Color(0xFFFFFFFF),
    error = Color(0xFFFF4444),
    errorContainer = Color(0xFFFF6666),
    onError = Color(0xFFFFFFFF),
    onErrorContainer = Color(0xFF000000),
    outline = Color(0xFF33334A),
    outlineVariant = Color(0xFF2A2A3A)
)

private val LightColorScheme = darkColorScheme(
    primary = Color(0xFFFF00FF),
    primaryContainer = Color(0xFFFF3366),
    secondary = Color(0xFF00FFFF),
    secondaryContainer = Color(0xFF00CCCC),
    tertiary = Color(0xFFFF6600),
    background = Color(0xFFF5F5F5),
    surface = Color(0xFFFFFFFF),
    surfaceVariant = Color(0xFFE0E0E0),
    surfaceContainer = Color(0xFFD5D5D5),
    onPrimary = Color(0xFF000000),
    onPrimaryContainer = Color(0xFFFFFFFF),
    onSecondary = Color(0xFF000000),
    onSecondaryContainer = Color(0xFFFFFFFF),
    onSurface = Color(0xFF000000),
    onSurfaceVariant = Color(0xFF606060),
    onBackground = Color(0xFF000000),
    error = Color(0xFFFF4444),
    errorContainer = Color(0xFFFF6666),
    onError = Color(0xFFFFFFFF),
    onErrorContainer = Color(0xFF000000),
    outline = Color(0xFFCCCCCC),
    outlineVariant = Color(0xFFB0B0B0)
)

@Composable
fun RJNXHyperEngineTheme(
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
            window.statusBarColor = colorScheme.background.toArgb()
            window.navigationBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
