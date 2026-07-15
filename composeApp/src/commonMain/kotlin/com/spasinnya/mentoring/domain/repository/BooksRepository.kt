package com.spasinnya.mentoring.domain.repository

import com.spasinnya.mentoring.domain.model.BookMeta
import com.spasinnya.mentoring.domain.model.DomainResult
import com.spasinnya.mentoring.domain.model.ParsedHomeworkWeek
import com.spasinnya.mentoring.domain.model.ParsedWeek
import com.spasinnya.mentoring.domain.model.PurchaseStatus
import kotlinx.coroutines.flow.Flow

interface BooksRepository {
    fun getBooks(): Flow<DomainResult<List<BookMeta>>>
    fun purchaseBook(bookId: String): Flow<DomainResult<PurchaseStatus>>
    fun getWeeks(bookId: String): Flow<DomainResult<BookMeta>>
    fun observeBook(bookId: String, weekNumber: Int): Flow<DomainResult<ParsedWeek>>
    fun observeHomework(bookId: String, weekNumber: Int): Flow<DomainResult<ParsedHomeworkWeek>>
}
