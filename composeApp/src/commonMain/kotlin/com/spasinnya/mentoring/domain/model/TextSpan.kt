package com.spasinnya.mentoring.domain.model

data class TextSpan(
    val text: String,
    val style: TextStyleMark = TextStyleMark(),
)
