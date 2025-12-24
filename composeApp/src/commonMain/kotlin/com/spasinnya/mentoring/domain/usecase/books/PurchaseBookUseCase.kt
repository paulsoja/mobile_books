package com.spasinnya.mentoring.domain.usecase.books

import com.spasinnya.mentoring.domain.model.PurchaseStatus
import com.spasinnya.mentoring.domain.repository.PurchaseBookRepository
import com.spasinnya.mentoring.domain.rules.DomainResult
import kotlinx.coroutines.flow.Flow

typealias PurchaseBookUseCase = suspend (bookId: Int) -> Flow<DomainResult<PurchaseStatus>>

fun purchaseBookUseCase(repository: PurchaseBookRepository): PurchaseBookUseCase = { bookId ->
    repository.invoke(bookId)
}