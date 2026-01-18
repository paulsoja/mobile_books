package com.spasinnya.mentoring.data.storage.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import okio.Path
import kotlin.time.ExperimentalTime

interface AppStore {
    fun flow(): Flow<Boolean>
    suspend fun read(): Boolean?
    suspend fun save(shown: Boolean)
    suspend fun clear()
}

private object AppStoreKeys {
    val CONGRATS_SHOWN = booleanPreferencesKey("congrats_shown")
}

@OptIn(ExperimentalTime::class)
internal fun appStoreFromPath(filePath: Path): AppStore {
    val ds: DataStore<Preferences> = PreferenceDataStoreFactory.createWithPath(
        corruptionHandler = null,
        migrations = emptyList(),
        produceFile = { filePath }
    )

    return object : AppStore {
        override fun flow(): Flow<Boolean> =
            ds.data
                .debounce(1000)
                .catch { emit(emptyPreferences()) }
                .map { p -> p[AppStoreKeys.CONGRATS_SHOWN] ?: false }
                .distinctUntilChanged()

        override suspend fun read(): Boolean? = flow().firstOrNull()

        override suspend fun save(shown: Boolean) {
            ds.edit { p -> p[AppStoreKeys.CONGRATS_SHOWN] = shown }
        }

        override suspend fun clear() { ds.edit { it.clear() } }
    }
}

expect fun provideAppStore(appContext: Any): AppStore