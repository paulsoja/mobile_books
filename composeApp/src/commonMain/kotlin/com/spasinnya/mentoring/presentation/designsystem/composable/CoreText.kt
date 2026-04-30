package com.spasinnya.mentoring.presentation.designsystem.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp

@Composable
fun CoreText(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    style: TextStyle = MaterialTheme.typography.bodySmall,
    textDecoration: TextDecoration = TextDecoration.None,
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        style = style,
        textDecoration = textDecoration,
    )
}

@Composable
fun CoreText(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    style: TextStyle = MaterialTheme.typography.bodySmall,
    textDecoration: TextDecoration = TextDecoration.None,
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        style = style,
        textDecoration = textDecoration,
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
    ),
    textDecoration: TextDecoration = TextDecoration.None,
) {
    CoreText(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        style = style,
        textDecoration = textDecoration
    )
}

@Composable
fun CoreTextBody(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    textDecoration: TextDecoration = TextDecoration.None,
    style: TextStyle = MaterialTheme.typography.bodyMedium.copy(
        color = Color(0xFF828EA0),
        fontWeight = FontWeight.Normal
    )
) {
    CoreText(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        style = style,
        textDecoration = textDecoration,
    )
}

@Composable
fun CoreTextBody(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    textDecoration: TextDecoration = TextDecoration.None,
    style: TextStyle = MaterialTheme.typography.bodyMedium.copy(
        color = Color(0xFF828EA0),
        fontWeight = FontWeight.Normal
    )
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        style = style,
        textDecoration = textDecoration,
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