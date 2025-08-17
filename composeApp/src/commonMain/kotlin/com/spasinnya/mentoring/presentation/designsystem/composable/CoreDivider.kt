package com.spasinnya.mentoring.presentation.designsystem.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun CoreHorizontalDivider(
    modifier: Modifier = Modifier,
) {
    HorizontalDivider(modifier = modifier, thickness = 1.dp, color = Color(0xFFA6B6CE))
}

@Composable
fun CoreHorizontalDividerWithText(
    text: String
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CoreHorizontalDivider(modifier = Modifier.weight(1f))
        CoreTextSubtitle(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = text,
            textAlign = TextAlign.Center
        )
        CoreHorizontalDivider(modifier = Modifier.weight(1f))
    }
}