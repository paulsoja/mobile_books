package com.spasinnya.mentoring.data.storage.datastore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStoreFile
import okio.Path.Companion.toOkioPath

actual fun provideTokenStore(appContext: Any): TokenStore {
    val context = appContext as Context
    val file = context.preferencesDataStoreFile("tokens.preferences").toOkioPath()
    return tokenStoreFromPath(file)
}