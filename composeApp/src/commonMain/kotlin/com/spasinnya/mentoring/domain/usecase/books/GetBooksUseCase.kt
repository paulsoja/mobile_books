package com.spasinnya.mentoring.domain.usecase.books

import com.spasinnya.mentoring.domain.model.BookMeta
import com.spasinnya.mentoring.domain.model.DomainResult
import com.spasinnya.mentoring.domain.repository.BooksRepository
import kotlinx.coroutines.flow.Flow

class GetBooksUseCase(
    private val booksRepository: BooksRepository
) {
    operator fun invoke(): Flow<DomainResult<List<BookMeta>>> =
        booksRepository.getBooks()
}
