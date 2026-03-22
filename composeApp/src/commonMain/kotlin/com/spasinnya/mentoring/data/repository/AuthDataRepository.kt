package com.spasinnya.mentoring.data.repository

import com.spasinnya.mentoring.data.mapper.toDomain
import com.spasinnya.mentoring.data.mapper.toDomainError
import com.spasinnya.mentoring.data.model.CredentialsApiRequest
import com.spasinnya.mentoring.data.model.OtpCredentialsApiRequest
import com.spasinnya.mentoring.data.model.OtpEmailApiRequest
import com.spasinnya.mentoring.data.model.TokenApiRequest
import com.spasinnya.mentoring.data.model.TokenApiResponse
import com.spasinnya.mentoring.data.net.postFlow
import com.spasinnya.mentoring.data.storage.datastore.AppStore
import com.spasinnya.mentoring.data.storage.datastore.LocaleStore
import com.spasinnya.mentoring.data.storage.datastore.TokenStore
import com.spasinnya.mentoring.domain.repository.LoginRepository
import com.spasinnya.mentoring.domain.repository.LogoutRepository
import com.spasinnya.mentoring.domain.repository.OtpRepository
import com.spasinnya.mentoring.domain.repository.RegisterRepository
import com.spasinnya.mentoring.domain.repository.RequestOtpRepository
import com.spasinnya.mentoring.presentation.base.alsoValidDo
import com.spasinnya.mentoring.presentation.base.map
import com.spasinnya.mentoring.presentation.base.mapErrors
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.map

fun loginRepository(
    http: HttpClient,
    tokenStore: TokenStore,
    appStore: AppStore
): LoginRepository = { creds ->
    http.postFlow<CredentialsApiRequest, TokenApiResponse>(
        path = "login",
        body = creds
    )
        .map { result ->
            result
                .map(TokenApiResponse::toDomain)
                .mapErrors { it.toDomainError() }
        }
        .alsoValidDo(tokenStore::save)
        .alsoValidDo { appStore.save(true) }
}

fun registerRepo(
    http: HttpClient,
): RegisterRepository = { creds ->
    http.postFlow<CredentialsApiRequest, String>(
        path = "register",
        body = creds
    )
        .map { result ->
            result
                .mapErrors {
                    it.toDomainError()
                }
        }
}

fun requestOtpCode(
    http: HttpClient,
): RequestOtpRepository = { email ->
    http.postFlow<OtpEmailApiRequest, Unit>(
        path = "request-otp",
        body = OtpEmailApiRequest(email.value)
    )
        .map { result ->
            result
                .mapErrors { it.toDomainError() }
        }
}

fun otpRepo(
    http: HttpClient,
    tokenStore: TokenStore
): OtpRepository = { creds ->
    http.postFlow<OtpCredentialsApiRequest, TokenApiResponse>(
        path = "verify-otp",
        body = creds
    )
        .map { result ->
            result
                .map(TokenApiResponse::toDomain)
                .mapErrors { it.toDomainError() }
        }
        .alsoValidDo(tokenStore::save)
}

fun logoutRepo(
    http: HttpClient,
    tokenStore: TokenStore,
    appStore: AppStore,
    localeStore: LocaleStore
): LogoutRepository = {
    val token = tokenStore.read() ?: throw Exception("Can't logout, no token")

    http.postFlow<TokenApiRequest, Unit>(
        path = "logout",
        body = TokenApiRequest(token.refreshToken)
    )
        .map { result ->
            result
                .mapErrors { it.toDomainError() }
        }
        .alsoValidDo { tokenStore.clear() }
        .alsoValidDo { appStore.clear() }
        .alsoValidDo { localeStore.clear() }
}