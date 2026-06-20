package com.spasinnya.mentoring.domain.mapper

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import com.spasinnya.mentoring.domain.model.RichParagraph
import com.spasinnya.mentoring.domain.model.TextStyleMark

fun RichParagraph.asAnnotatedString(
    highlightColor: Color,
): AnnotatedString = buildAnnotatedString {
    spans.forEach { span ->
        withStyle(span.style.toSpanStyle(highlightColor)) {
            append(span.text)
        }
    }
}

private fun TextStyleMark.toSpanStyle(
    highlightColor: Color,
): SpanStyle = SpanStyle(
    fontWeight = if (bold) FontWeight.Bold else null,
    fontStyle = if (italic) FontStyle.Italic else null,
    textDecoration = if (underline) TextDecoration.Underline else null,
    background = if (highlighted) highlightColor else Color.Unspecified,
)