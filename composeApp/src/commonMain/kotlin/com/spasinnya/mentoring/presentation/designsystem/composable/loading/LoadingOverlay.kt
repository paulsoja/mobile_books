package com.spasinnya.mentoring.presentation.designsystem.composable.loading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.zIndex
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreCircularProgressIndicator

@Composable
fun LoadingOverlay(visible: Boolean) {
    if (!visible) return

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x66F5F7FC))
            .zIndex(4f),
        contentAlignment = Alignment.Center
    ) {
        CoreCircularProgressIndicator()
    }
}