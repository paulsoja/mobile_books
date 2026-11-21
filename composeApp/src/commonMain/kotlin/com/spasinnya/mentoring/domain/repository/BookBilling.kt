package com.spasinnya.mentoring.domain.repository

import com.spasinnya.mentoring.domain.model.BookMeta
import com.spasinnya.mentoring.domain.model.PurchaseStatus

/** Platform store integration. Access is granted only after backend verification. */
interface BookBilling {
    suspend fun refresh(books: List<BookMeta>): List<BookMeta>
    suspend fun purchase(book: BookMeta): PurchaseStatus
}
