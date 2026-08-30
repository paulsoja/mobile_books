package com.spasinnya.mentoring.domain.usecase.homework

import com.spasinnya.mentoring.domain.model.DomainResult
import com.spasinnya.mentoring.domain.model.Homework
import com.spasinnya.mentoring.domain.repository.HomeworkRepository
import com.spasinnya.mentoring.presentation.base.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetHomeworkUseCase(
    private val homeworkRepository: HomeworkRepository,
) {
    operator fun invoke(): Flow<DomainResult<List<Homework>>> {
        return homeworkRepository.getHomework().map {
            it.map {
                it.map {
                    it.copy(
                        completedLessons = it.completedLessons * 100 / 50
                    )
                }
            }
        }
    }
}
