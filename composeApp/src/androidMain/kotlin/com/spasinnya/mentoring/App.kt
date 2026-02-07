package com.spasinnya.mentoring

import android.app.Application
import com.google.firebase.FirebaseApp
import com.spasinnya.mentoring.data.net.plugin.NetStatus
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Napier.base(DebugAntilog())
        NetStatus.init(this)
        FirebaseApp.initializeApp(this)
    }
}