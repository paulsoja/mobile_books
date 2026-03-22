package com.spasinnya.mentoring.domain.usecase.books

import com.spasinnya.mentoring.domain.model.DomainResult
import com.spasinnya.mentoring.domain.model.Lesson
import com.spasinnya.mentoring.domain.repository.LessonsRepository
import kotlinx.coroutines.flow.Flow

typealias GetLessonsUseCase = suspend (weekId: Int) -> Flow<DomainResult<List<Lesson>>>

fun lessonsUseCase(repository: LessonsRepository): GetLessonsUseCase = { weekId ->
    repository.invoke(weekId)
}