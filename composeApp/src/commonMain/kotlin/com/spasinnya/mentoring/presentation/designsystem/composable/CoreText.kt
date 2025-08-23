package com.spasinnya.mentoring.presentation.designsystem.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun CoreText(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    style: TextStyle = MaterialTheme.typography.bodySmall
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        style = style
    )
}

@Composable
fun CoreTextSubtitle(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    style: TextStyle = MaterialTheme.typography.titleLarge.copy(
        fontSize = 18.sp,
        color = Color(0xFF828EA0)
    )
) {
    CoreText(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        style = style
    )
}

@Composable
fun CoreTextBody(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    style: TextStyle = MaterialTheme.typography.bodyMedium.copy(
        color = Color(0xFF828EA0),
        fontWeight = FontWeight.Normal
    )
) {
    CoreText(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        style = style
    )
}

@Composable
fun CoreTextScreenTitle(
    text: String,
    modifier: Modifier = Modifier.fillMaxWidth(),
    textAlign: TextAlign = TextAlign.Start,
    color: Color = Color(0xFF3C4E73)
) {
    CoreText(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        style = MaterialTheme.typography.headlineSmall.copy(
            fontWeight = FontWeight.Bold,
            color = color
        )
    )
}

@Composable
fun CoreTextTitle(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = Color(0xFF3C4E73)
) {
    CoreText(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        style = MaterialTheme.typography.headlineSmall.copy(
            fontWeight = FontWeight.Bold,
            color = color,
            fontSize = 18.sp,
            lineHeight = 22.sp
        )
    )
}