package com.spasinnya.mentoring.di

import com.spasinnya.mentoring.data.storage.datastore.provideLocaleStore
import com.spasinnya.mentoring.data.storage.datastore.provideTokenStore
import org.koin.dsl.module

actual fun platformModule() = module {
    single { provideTokenStore(Any()) }
    single { provideLocaleStore(Any()) }
}
