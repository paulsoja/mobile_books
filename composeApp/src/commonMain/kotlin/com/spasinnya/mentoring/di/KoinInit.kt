package com.spasinnya.mentoring.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            platformModule(),
            dataModule,
            repositoryModule,
            useCaseModule,
            viewModelModule,
        )
    }

// called by iOS
fun initKoin() = initKoin {}

expect fun platformModule(): Module
