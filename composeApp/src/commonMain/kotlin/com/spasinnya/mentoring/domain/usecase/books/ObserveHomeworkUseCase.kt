package com.spasinnya.mentoring.domain.usecase.books

import com.spasinnya.mentoring.domain.model.DomainResult
import com.spasinnya.mentoring.domain.model.ParsedHomeworkWeek
import com.spasinnya.mentoring.domain.repository.BooksRepository
import kotlinx.coroutines.flow.Flow

class ObserveHomeworkUseCase(
    private val booksRepository: BooksRepository
) {
    operator fun invoke(bookId: String, weekNumber: Int): Flow<DomainResult<ParsedHomeworkWeek>> =
        booksRepository.observeHomework(bookId, weekNumber)
}
