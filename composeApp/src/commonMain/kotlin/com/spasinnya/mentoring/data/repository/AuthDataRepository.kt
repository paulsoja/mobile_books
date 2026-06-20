package com.spasinnya.mentoring.data.repository

import com.spasinnya.mentoring.data.mapper.toDomain
import com.spasinnya.mentoring.data.mapper.toDomainError
import com.spasinnya.mentoring.data.model.*
import com.spasinnya.mentoring.data.net.postFlow
import com.spasinnya.mentoring.data.storage.datastore.AppStore
import com.spasinnya.mentoring.data.storage.datastore.LocaleStore
import com.spasinnya.mentoring.data.storage.datastore.TokenStore
import com.spasinnya.mentoring.domain.model.DomainResult
import com.spasinnya.mentoring.domain.model.Email
import com.spasinnya.mentoring.domain.model.Token
import com.spasinnya.mentoring.domain.repository.AuthRepository
import com.spasinnya.mentoring.presentation.base.alsoValidDo
import com.spasinnya.mentoring.presentation.base.map
import com.spasinnya.mentoring.presentation.base.mapErrors
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class AuthDataRepository(
    private val http: HttpClient,
    private val tokenStore: TokenStore,
    private val appStore: AppStore,
    private val localeStore: LocaleStore,
) : AuthRepository {

    override fun login(credentials: CredentialsApiRequest): Flow<DomainResult<Token>> =
        http.postFlow<CredentialsApiRequest, TokenApiResponse>(
            path = "login",
            body = credentials
        ).map { result ->
            result
                .map(TokenApiResponse::toDomain)
                .mapErrors { it.toDomainError() }
        }.alsoValidDo(tokenStore::save)
            .alsoValidDo { appStore.save(true) }

    override fun register(credentials: CredentialsApiRequest): Flow<DomainResult<String>> =
        http.postFlow<CredentialsApiRequest, String>(
            path = "register",
            body = credentials
        ).map { result ->
            result.mapErrors { it.toDomainError() }
        }

    override fun otp(credentials: OtpCredentialsApiRequest): Flow<DomainResult<Token>> =
        http.postFlow<OtpCredentialsApiRequest, TokenApiResponse>(
            path = "verify-otp",
            body = credentials
        ).map { result ->
            result
                .map(TokenApiResponse::toDomain)
                .mapErrors { it.toDomainError() }
        }.alsoValidDo(tokenStore::save)

    override fun requestOtp(email: Email.Valid): Flow<DomainResult<Unit>> =
        http.postFlow<OtpEmailApiRequest, Unit>(
            path = "request-otp",
            body = OtpEmailApiRequest(email.value)
        ).map { result ->
            result.mapErrors { it.toDomainError() }
        }

    override fun logout(): Flow<DomainResult<Unit>> = flow {
        val token = tokenStore.read() ?: throw Exception("Can't logout, no token")
        
        http.postFlow<TokenApiRequest, Unit>(
            path = "logout",
            body = TokenApiRequest(token.refreshToken)
        ).map { result ->
            result.mapErrors { it.toDomainError() }
        }.alsoValidDo { tokenStore.clear() }
            .alsoValidDo { appStore.clear() }
            .alsoValidDo { localeStore.clear() }
            .collect { emit(it) }
    }
}
