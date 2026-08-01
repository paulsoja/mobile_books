package com.spasinnya.mentoring.domain.usecase.books

import com.spasinnya.mentoring.domain.model.DomainResult
import com.spasinnya.mentoring.domain.model.HomeworkAnswer
import com.spasinnya.mentoring.domain.repository.BooksRepository
import kotlinx.coroutines.flow.Flow

class SaveHomeworkAnswersUseCase(
    private val booksRepository: BooksRepository
) {
    operator fun invoke(
        bookId: String,
        weekNumber: Int,
        lessonNumber: Int,
        answers: List<HomeworkAnswer>,
    ): Flow<DomainResult<Unit>> =
        booksRepository.saveHomeworkAnswers(bookId, weekNumber, lessonNumber, answers)
}
