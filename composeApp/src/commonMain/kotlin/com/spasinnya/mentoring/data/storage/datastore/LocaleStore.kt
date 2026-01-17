package com.spasinnya.mentoring.data.storage.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import io.github.aakira.napier.Napier
import io.github.aakira.napier.Napier.e
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import okio.Path
import kotlin.time.ExperimentalTime

interface LocaleStore {
    fun flow(): Flow<String?>
    suspend fun read(): String?
    suspend fun save(langTag: String)
    suspend fun clear()
}

private object LocaleStoreKeys {
    val LocaleTagKey = stringPreferencesKey("locale_tag")
}

@OptIn(ExperimentalTime::class)
internal fun localeStoreFromPath(filePath: Path): LocaleStore {
    val ds: DataStore<Preferences> = PreferenceDataStoreFactory.createWithPath(
        corruptionHandler = null,
        migrations = emptyList(),
        produceFile = { filePath }
    )

    return object : LocaleStore {
        override fun flow(): Flow<String?> =
            ds.data
                .catch {
                    Napier.e(it) { "LocaleStore: ds.data failed. filePath=$filePath" }
                    emit(emptyPreferences())
                }
                .map { p -> p[LocaleStoreKeys.LocaleTagKey] }
                .distinctUntilChanged()

        override suspend fun read(): String? = flow().firstOrNull()

        override suspend fun save(langTag: String) {
            Napier.d { "LocaleStore: save langTag=$langTag" }
            ds.edit { p -> p[LocaleStoreKeys.LocaleTagKey] = langTag }
        }

        override suspend fun clear() { ds.edit { it.clear() } }
    }
}

expect fun provideLocaleStore(appContext: Any): LocaleStore