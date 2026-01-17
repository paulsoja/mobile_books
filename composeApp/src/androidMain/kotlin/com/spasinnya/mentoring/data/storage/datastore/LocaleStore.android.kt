package com.spasinnya.mentoring.data.storage.datastore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStoreFile
import okio.Path
import okio.Path.Companion.toOkioPath

@Volatile private var localeStoreInstance: LocaleStore? = null
private val localeStoreLock = Any()

actual fun provideLocaleStore(appContext: Any): LocaleStore {
    val context = (appContext as Context).applicationContext
    val file: Path = context.preferencesDataStoreFile("locale.preferences").toOkioPath()

    return localeStoreInstance ?: synchronized(localeStoreLock) {
        localeStoreInstance ?: localeStoreFromPath(file).also { localeStoreInstance = it }
    }
}