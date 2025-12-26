package com.spasinnya.mentoring.presentation.di

import com.spasinnya.mentoring.data.storage.datastore.AppStore
import com.spasinnya.mentoring.data.storage.datastore.TokenStore
import com.spasinnya.mentoring.data.storage.datastore.provideAppStore
import com.spasinnya.mentoring.data.storage.datastore.provideTokenStore

fun provideDataSourceModule(context: Any): DataSourceModule =
    object : DataSourceModule {
        override val appStore: AppStore by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            provideAppStore(context)
        }
        override val tokenStore: TokenStore by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            provideTokenStore(context)
        }
    }