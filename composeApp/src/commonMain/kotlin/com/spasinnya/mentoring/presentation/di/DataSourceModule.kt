package com.spasinnya.mentoring.presentation.di

import com.spasinnya.mentoring.data.storage.datastore.AppStore
import com.spasinnya.mentoring.data.storage.datastore.TokenStore

interface DataSourceModule {
    val appStore: AppStore
    val tokenStore: TokenStore
}