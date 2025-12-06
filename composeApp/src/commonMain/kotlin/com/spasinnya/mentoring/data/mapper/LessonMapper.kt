package com.spasinnya.mentoring.data.mapper

import com.spasinnya.mentoring.data.model.LessonContentResponse
import com.spasinnya.mentoring.data.model.LessonResponse
import com.spasinnya.mentoring.domain.model.Lesson
import com.spasinnya.mentoring.domain.model.LessonContent
import com.spasinnya.mentoring.domain.model.LessonContentType

fun LessonResponse.toDomain(): Lesson = Lesson(
    id = id.toInt(),
    weekId = weekId.toInt(),
    number = number,
    title = title,
    quote = quote,
    content = content.map(LessonContentResponse::toDomain)
)

fun LessonContentResponse.toDomain(): LessonContent = LessonContent(
    type = LessonContentType.valueOf(type),
    data = data
)