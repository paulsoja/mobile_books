package com.spasinnya.mentoring.presentation.designsystem

import androidx.compose.material.Colors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

interface ColorPalette {
    val materialColors: Colors
}

@Composable
fun colorPalette(): ColorPalette = object : ColorPalette {
    override val materialColors: Colors = lightColors()
}