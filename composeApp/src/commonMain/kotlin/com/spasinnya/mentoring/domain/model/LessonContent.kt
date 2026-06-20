package com.spasinnya.mentoring.domain.model

data class LessonContent(
    val quotes: List<RichParagraph>,
    val body: List<LessonBlock>,
)
