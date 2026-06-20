package com.spasinnya.mentoring.domain.model

data class ParsedWeek(
    val weekNumber: Int,
    val weekTitle: String,
    val lessons: List<ParsedLesson>,
)
