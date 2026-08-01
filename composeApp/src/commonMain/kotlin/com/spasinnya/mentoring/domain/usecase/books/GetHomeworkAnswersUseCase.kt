package com.spasinnya.mentoring.domain.usecase.books

import com.spasinnya.mentoring.domain.model.DomainResult
import com.spasinnya.mentoring.domain.model.HomeworkAnswer
import com.spasinnya.mentoring.domain.repository.BooksRepository
import kotlinx.coroutines.flow.Flow

class GetHomeworkAnswersUseCase(
    private val booksRepository: BooksRepository
) {
    operator fun invoke(
        bookId: String,
        weekNumber: Int,
        lessonNumber: Int,
    ): Flow<DomainResult<List<HomeworkAnswer>>> =
        booksRepository.getHomeworkAnswers(bookId, weekNumber, lessonNumber)
}
