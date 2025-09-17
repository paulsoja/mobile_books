package com.spasinnya.mentoring

import android.app.Application
import com.spasinnya.mentoring.data.net.createHttpClient
import com.spasinnya.mentoring.data.net.plugin.NetStatus
import com.spasinnya.mentoring.data.storage.datastore.provideTokenStore
import com.spasinnya.mentoring.presentation.di.provideRepoModule
import com.spasinnya.mentoring.presentation.di.provideUseCaseModule
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

class App : Application() {

    val tokenStore by lazy { provideTokenStore(applicationContext) }
    val http       by lazy { createHttpClient(tokenStore) }

    val repos by lazy { provideRepoModule(http, tokenStore) }
    val useCases by lazy { provideUseCaseModule(repos) }

    override fun onCreate() {
        super.onCreate()

        Napier.base(DebugAntilog())
        NetStatus.init(this)
    }
}