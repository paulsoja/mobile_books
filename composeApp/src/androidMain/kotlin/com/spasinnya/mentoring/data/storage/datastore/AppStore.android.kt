package com.spasinnya.mentoring.data.storage.datastore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStoreFile
import okio.Path
import okio.Path.Companion.toOkioPath

@Volatile private var appStoreInstance: AppStore? = null
private val appStoreLock = Any()

actual fun provideAppStore(appContext: Any): AppStore {
    val context = (appContext as Context).applicationContext
    val file: Path = context.preferencesDataStoreFile("app.preferences").toOkioPath()

    return appStoreInstance ?: synchronized(appStoreLock) {
        appStoreInstance ?: appStoreFromPath(file).also { appStoreInstance = it }
    }
}