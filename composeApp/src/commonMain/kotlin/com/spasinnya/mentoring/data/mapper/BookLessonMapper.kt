package com.spasinnya.mentoring.data.mapper

import com.spasinnya.mentoring.data.model.BookLessonResponse
import com.spasinnya.mentoring.domain.model.BookLesson

fun BookLessonResponse.toDomain(): BookLesson {
    return BookLesson(
        lessonNumber = lessonNumber,
        title = title
    )
}