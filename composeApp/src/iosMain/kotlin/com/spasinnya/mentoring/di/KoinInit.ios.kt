package com.spasinnya.mentoring.di

import com.spasinnya.mentoring.data.storage.datastore.provideLocaleStore
import com.spasinnya.mentoring.data.storage.datastore.provideTokenStore
import org.koin.dsl.module

actual fun platformModule() = module {
    single<com.spasinnya.mentoring.domain.repository.BookBilling> {
        object : com.spasinnya.mentoring.domain.repository.BookBilling {
            override suspend fun refresh(books: List<com.spasinnya.mentoring.domain.model.BookMeta>) = books
            override suspend fun purchase(book: com.spasinnya.mentoring.domain.model.BookMeta): com.spasinnya.mentoring.domain.model.PurchaseStatus =
                error("Purchases are not available on iOS yet")
        }
    }
    single { provideTokenStore(Any()) }
    single { provideLocaleStore(Any()) }
}
