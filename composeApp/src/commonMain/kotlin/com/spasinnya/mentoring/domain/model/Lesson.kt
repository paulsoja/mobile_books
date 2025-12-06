package com.spasinnya.mentoring.domain.model

data class Lesson(
    val id: Int,
    val weekId: Int,
    val number: Int,
    val title: String,
    val quote: String? = null,
    val content: List<LessonContent>
)
