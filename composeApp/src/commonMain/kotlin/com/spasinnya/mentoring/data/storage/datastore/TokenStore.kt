package com.spasinnya.mentoring.data.storage.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.spasinnya.mentoring.domain.model.Token
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import okio.Path
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

interface TokenStore {
    fun flow(): Flow<Token?>
    suspend fun read(): Token?
    suspend fun save(token: Token)
    suspend fun clear()
}

private object Keys {
    val ACCESS = stringPreferencesKey("access")
    val ACCESS_AT = stringPreferencesKey("access_expires_at")   // ISO-строка
    val REFRESH = stringPreferencesKey("refresh")
    val REFRESH_AT = stringPreferencesKey("refresh_expires_at") // ISO-строка
}

@OptIn(ExperimentalTime::class)
internal fun tokenStoreFromPath(filePath: Path): TokenStore {
    val ds: DataStore<Preferences> = PreferenceDataStoreFactory.createWithPath(
        corruptionHandler = null,
        migrations = emptyList(),
        produceFile = { filePath }
    )

    return object : TokenStore {
        override fun flow(): Flow<Token?> =
            ds.data
                .catch { emit(emptyPreferences()) }
                .map { p ->
                    val a = p[Keys.ACCESS]
                    val aAt = p[Keys.ACCESS_AT]
                    val r = p[Keys.REFRESH]
                    val rAt = p[Keys.REFRESH_AT]
                    if (a != null && aAt != null && r != null && rAt != null) {
                        Token(
                            accessToken = a,
                            accessExpiresAt = aAt.parseInstantOrNull() ?: return@map null,
                            refreshToken = r,
                            refreshExpiresAt = rAt.parseInstantOrNull() ?: return@map null
                        )
                    } else null
                }
                .distinctUntilChanged()

        override suspend fun read(): Token? = flow().first()

        override suspend fun save(token: Token) {
            ds.edit { p ->
                p[Keys.ACCESS] = token.accessToken
                p[Keys.ACCESS_AT] = token.accessExpiresAt.toIsoString()
                p[Keys.REFRESH] = token.refreshToken
                p[Keys.REFRESH_AT] = token.refreshExpiresAt.toIsoString()
            }
        }

        override suspend fun clear() { ds.edit { it.clear() } }
    }
}

@OptIn(ExperimentalTime::class)
private fun Instant.toIsoString(): String = toString()

@OptIn(ExperimentalTime::class)
private fun String.parseInstantOrNull(): Instant? =
    try { Instant.parse(this) } catch (_: Throwable) { null }

expect fun provideTokenStore(appContext: Any): TokenStore