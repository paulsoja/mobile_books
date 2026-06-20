package com.spasinnya.mentoring.di

import android.content.Context
import com.spasinnya.mentoring.data.storage.datastore.provideAppStore
import com.spasinnya.mentoring.data.storage.datastore.provideLocaleStore
import com.spasinnya.mentoring.data.storage.datastore.provideTokenStore
import org.koin.dsl.module

actual fun platformModule() = module {
    single { provideAppStore(get<Context>()) }
    single { provideTokenStore(get<Context>()) }
    single { provideLocaleStore(get<Context>()) }
}
