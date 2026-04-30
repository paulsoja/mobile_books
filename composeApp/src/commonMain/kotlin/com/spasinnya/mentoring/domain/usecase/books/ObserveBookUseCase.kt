package com.spasinnya.mentoring.domain.usecase.books

import com.spasinnya.mentoring.domain.model.DomainResult
import com.spasinnya.mentoring.domain.model.ParsedWeek
import com.spasinnya.mentoring.domain.repository.BooksRepository
import kotlinx.coroutines.flow.Flow

class ObserveBookUseCase(
    private val booksRepository: BooksRepository
) {
    operator fun invoke(bookId: String, weekNumber: Int): Flow<DomainResult<ParsedWeek>> =
        booksRepository.observeBook(bookId, weekNumber)
}
