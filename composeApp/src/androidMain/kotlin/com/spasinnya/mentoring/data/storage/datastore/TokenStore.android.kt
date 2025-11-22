package com.spasinnya.mentoring.data.storage.datastore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStoreFile
import okio.Path
import okio.Path.Companion.toOkioPath

@Volatile private var tokenStoreInstance: TokenStore? = null
private val tokenStoreLock = Any()

actual fun provideTokenStore(appContext: Any): TokenStore {
    val context = (appContext as Context).applicationContext
    val file: Path = context.preferencesDataStoreFile("tokens.preferences").toOkioPath()

    return tokenStoreInstance ?: synchronized(tokenStoreLock) {
        tokenStoreInstance ?: tokenStoreFromPath(file).also { tokenStoreInstance = it }
    }
}