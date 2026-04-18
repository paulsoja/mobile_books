package com.spasinnya.mentoring.domain.usecase.books

import com.spasinnya.mentoring.domain.model.BookMeta
import com.spasinnya.mentoring.domain.model.DomainResult
import com.spasinnya.mentoring.domain.repository.BooksRepository
import kotlinx.coroutines.flow.Flow

typealias GetBooksUseCase = suspend () -> Flow<DomainResult<List<BookMeta>>>

fun getBooksUseCase(repository: BooksRepository): GetBooksUseCase = {
    repository.invoke()
}