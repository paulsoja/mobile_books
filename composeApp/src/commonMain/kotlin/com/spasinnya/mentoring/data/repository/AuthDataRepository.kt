package com.spasinnya.mentoring.data.repository

import com.spasinnya.mentoring.data.mapper.toData
import com.spasinnya.mentoring.data.mapper.toDomain
import com.spasinnya.mentoring.data.mapper.toDomainError
import com.spasinnya.mentoring.data.model.*
import com.spasinnya.mentoring.data.net.postFlow
import com.spasinnya.mentoring.data.storage.datastore.AppStore
import com.spasinnya.mentoring.data.storage.datastore.LocaleStore
import com.spasinnya.mentoring.data.storage.datastore.TokenStore
import com.spasinnya.mentoring.domain.enums.OtpPurpose
import com.spasinnya.mentoring.domain.model.DomainError
import com.spasinnya.mentoring.domain.model.DomainResult
import com.spasinnya.mentoring.domain.model.Email
import com.spasinnya.mentoring.domain.model.Token
import com.spasinnya.mentoring.domain.repository.AuthRepository
import com.spasinnya.mentoring.presentation.base.Validated
import com.spasinnya.mentoring.presentation.base.alsoValidDo
import com.spasinnya.mentoring.presentation.base.map
import com.spasinnya.mentoring.presentation.base.mapErrors
import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.authProvider
import io.ktor.client.plugins.auth.providers.BearerAuthProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
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
                .mapErrors { it.toDomainError().asSignInError() }
        }.alsoValidDo(::saveSession)
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
        }.alsoValidDo(::saveSession)

    override fun requestOtp(email: Email.Valid, purpose: OtpPurpose): Flow<DomainResult<Unit>> =
        http.postFlow<OtpEmailApiRequest, Unit>(
            path = "request-otp",
            body = OtpEmailApiRequest(email = email.value, purpose = purpose.toData())
        ).map { result ->
            result.mapErrors { it.toDomainError() }
        }

    override fun resetPassword(request: ResetPasswordApiRequest): Flow<DomainResult<Unit>> =
        http.postFlow<ResetPasswordApiRequest, Unit>(
            path = "reset-password",
            body = request
        ).map { result ->
            result.mapErrors { it.toDomainError() }
        }

    override fun logout(): Flow<DomainResult<Unit>> = flow {
        val token = tokenStore.read()

        val result = token?.let {
            http.postFlow<TokenApiRequest, Unit>(
                path = "logout",
                body = TokenApiRequest(it.refreshToken)
            ).map { response ->
                response.mapErrors { error -> error.toDomainError() }
            }.first()
        } ?: Validated.Valid(Unit)

        clearSession()

        emit(result)
    }

    /** A 401 from the sign-in endpoint means the email/password pair is wrong, not a dead session. */
    private fun DomainError.asSignInError(): DomainError =
        if (this == DomainError.Unauthorized) DomainError.InvalidCredentials else this

    private suspend fun saveSession(token: Token) {
        tokenStore.save(token)
        http.authProvider<BearerAuthProvider>()?.clearToken()
    }

    private suspend fun clearSession() {
        tokenStore.clear()
        appStore.clear()
        localeStore.clear()
        http.authProvider<BearerAuthProvider>()?.clearToken()
    }
}
