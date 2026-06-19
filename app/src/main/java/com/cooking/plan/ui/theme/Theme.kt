package com.cooking.plan.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat

private val Tomato = Color(0xFFD94B34)
private val TomatoDark = Color(0xFF9F2C1D)
private val Basil = Color(0xFF2E7D5B)
private val BasilDark = Color(0xFF154B38)
private val Saffron = Color(0xFFF2B84B)
private val Porcelain = Color(0xFFFFFCF7)
private val PrepBoard = Color(0xFFF4EFE5)
private val Linen = Color(0xFFE9DFD1)
private val Ink = Color(0xFF221915)

private val LightColorScheme = lightColorScheme(
    primary = Tomato,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFD9D0),
    onPrimaryContainer = Color(0xFF3B0903),
    secondary = Basil,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFCBEBDD),
    onSecondaryContainer = Color(0xFF062116),
    tertiary = Saffron,
    onTertiary = Color(0xFF2D1B00),
    tertiaryContainer = Color(0xFFFFE6AC),
    onTertiaryContainer = Color(0xFF2D1B00),
    surface = Porcelain,
    onSurface = Ink,
    surfaceVariant = PrepBoard,
    onSurfaceVariant = Color(0xFF675B52),
    background = Color(0xFFFBF3E8),
    outline = Color(0xFFCBBCAE),
    outlineVariant = Linen,
    error = Color(0xFFB3261E)
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFFFB4A5),
    onPrimary = Color(0xFF5F150B),
    primaryContainer = TomatoDark,
    onPrimaryContainer = Color(0xFFFFD9D0),
    secondary = Color(0xFFA9D8C3),
    onSecondary = Color(0xFF123729),
    secondaryContainer = BasilDark,
    onSecondaryContainer = Color(0xFFCBEBDD),
    tertiary = Color(0xFFFFD37A),
    onTertiary = Color(0xFF442B00),
    tertiaryContainer = Color(0xFF6A4300),
    onTertiaryContainer = Color(0xFFFFE6AC),
    surface = Color(0xFF211A16),
    onSurface = Color(0xFFF4ECE5),
    surfaceVariant = Color(0xFF332822),
    onSurfaceVariant = Color(0xFFD8C8BC),
    background = Color(0xFF17110E),
    outline = Color(0xFF9E8F82),
    outlineVariant = Color(0xFF4E4038),
)

private val KitchenTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 17.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 15.sp,
        lineHeight = 21.sp,
        letterSpacing = 0.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 17.sp,
        letterSpacing = 0.sp
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.sp
    )
)

@Composable
fun CookingPlanTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
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
            val insetsController = WindowCompat.getInsetsController(window, view)
            insetsController.isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = KitchenTypography,
        content = content
    )
}
