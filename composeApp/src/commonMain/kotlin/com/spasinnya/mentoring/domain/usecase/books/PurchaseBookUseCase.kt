package com.spasinnya.mentoring.domain.usecase.books

import com.spasinnya.mentoring.domain.model.DomainResult
import com.spasinnya.mentoring.domain.model.PurchaseStatus
import com.spasinnya.mentoring.domain.repository.BooksRepository
import kotlinx.coroutines.flow.Flow

class PurchaseBookUseCase(
    private val booksRepository: BooksRepository
) {
    operator fun invoke(bookId: String): Flow<DomainResult<PurchaseStatus>> =
        booksRepository.purchaseBook(bookId)
}
