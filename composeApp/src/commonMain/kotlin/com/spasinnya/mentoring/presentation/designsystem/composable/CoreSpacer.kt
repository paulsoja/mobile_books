package com.spasinnya.mentoring.presentation.designsystem.composable

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CoreSpacerVertical(height: Dp = 16.dp) {
    Spacer(modifier = Modifier.height(height))
}

@Composable
fun CoreSpacerVerticalMedium() {
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun CoreSpacerVerticalSmall() {
    Spacer(modifier = Modifier.height(4.dp))
}

@Composable
fun CoreSpacerVerticalLarge() {
    Spacer(modifier = Modifier.height(24.dp))
}

@Composable
fun CoreSpacerVerticalX2() {
    Spacer(modifier = Modifier.height(32.dp))
}

@Composable
fun CoreSpacerVerticalXLarge() {
    Spacer(modifier = Modifier.height(36.dp))
}

@Composable
fun RowScope.CoreSpacerHorizontalWeight(weight: Float = 1f) {
    Spacer(modifier = Modifier.weight(weight))
}

@Composable
fun ColumnScope.CoreSpacerVerticalWeight(weight: Float = 1f) {
    Spacer(modifier = Modifier.weight(weight))
}
