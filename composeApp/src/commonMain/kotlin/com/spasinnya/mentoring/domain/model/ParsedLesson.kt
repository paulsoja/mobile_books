package com.spasinnya.mentoring.domain.model

data class ParsedLesson(
    val lessonNumber: Int,
    val title: String,
    val quotes: List<String>,
    val body: String,
)
