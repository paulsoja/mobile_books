package com.spasinnya.mentoring.domain.repository

import com.spasinnya.mentoring.domain.model.BookMeta
import com.spasinnya.mentoring.domain.model.DomainResult
import com.spasinnya.mentoring.domain.model.ParsedWeek
import com.spasinnya.mentoring.domain.model.PurchaseStatus
import kotlinx.coroutines.flow.Flow

typealias BooksRepository = suspend () -> Flow<DomainResult<List<BookMeta>>>
typealias PurchaseBookRepository = suspend (bookId: String) -> Flow<DomainResult<PurchaseStatus>>
typealias WeeksRepository = suspend (bookId: String) -> Flow<DomainResult<BookMeta>>
typealias BookReaderRepository = suspend (bookId: String, weekNumber: Int) -> Flow<DomainResult<ParsedWeek>>