package com.spasinnya.mentoring.di

import android.content.Context
import com.spasinnya.mentoring.data.storage.datastore.provideLocaleStore
import com.spasinnya.mentoring.data.storage.datastore.provideTokenStore
import org.koin.dsl.module

actual fun platformModule() = module {
    single { com.spasinnya.mentoring.billing.AndroidBookBilling(get<Context>(), get()) }
    single<com.spasinnya.mentoring.domain.repository.BookBilling> { get<com.spasinnya.mentoring.billing.AndroidBookBilling>() }
    single { provideTokenStore(get<Context>()) }
    single { provideLocaleStore(get<Context>()) }
}
