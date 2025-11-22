package com.spasinnya.mentoring.domain.usecase.books

import com.spasinnya.mentoring.domain.model.ShortBook
import com.spasinnya.mentoring.domain.repository.BooksRepository
import kotlinx.coroutines.flow.Flow

typealias GetBooksUseCase = suspend () -> Flow<List<ShortBook>>

fun getBooksUseCase(repository: BooksRepository): GetBooksUseCase = {
    repository.invoke()
}