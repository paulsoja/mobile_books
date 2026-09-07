package com.spasinnya.mentoring.data.repository

import com.spasinnya.mentoring.data.storage.datastore.LocaleStore
import com.spasinnya.mentoring.data.storage.datastore.TokenStore
import com.spasinnya.mentoring.domain.model.DomainError
import com.spasinnya.mentoring.domain.model.DomainResult
import com.spasinnya.mentoring.domain.model.Token
import com.spasinnya.mentoring.domain.repository.PrefRepository
import com.spasinnya.mentoring.presentation.base.Validated
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class PrefDataRepository(
    private val tokenStore: TokenStore,
    private val localeStore: LocaleStore,
) : PrefRepository {

    override fun getToken(): Flow<DomainResult<Token>> =
        tokenStore.flow().map { token ->
            token?.let { Validated.Valid(it) }
                ?: Validated.Invalid(DomainError.NotFound)
        }

    override fun getLocale(): Flow<DomainResult<String?>> {
        return localeStore.flow()
            .map { lang ->
                lang?.let { Validated.Valid(it) }
                    ?:Validated.Invalid(DomainError.NotFound)
            }
    }

    override suspend fun getLocaleTag(): String? = localeStore.read()

    override fun setLocale(locale: String): Flow<DomainResult<Unit>> = flow {
        localeStore.save(locale)
        emit(Validated.Valid(Unit))
    }
}
