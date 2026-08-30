package com.spasinnya.mentoring.data.mapper

import com.spasinnya.mentoring.data.model.HomeworkApiResponse
import com.spasinnya.mentoring.domain.model.Homework

fun HomeworkApiResponse.toDomain() = Homework(
    bookId = bookId,
    completedLessons = completedLessons,
)
