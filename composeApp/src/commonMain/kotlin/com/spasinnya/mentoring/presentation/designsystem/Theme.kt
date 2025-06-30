package com.spasinnya.mentoring.presentation.designsystem

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

@Composable
fun BooksTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    colors: ColorPalette = colorPalette(),
    content: @Composable () -> Unit,
) {

    CompositionLocalProvider(
        LocalColors provides colors,
    ) {
        MaterialTheme(
            colors = colors.materialColors,
            typography = appTypography(),
            content = content
        )
    }
}

val LocalColors = staticCompositionLocalOf<ColorPalette> {
    error("No ColorPalette provided")
}