package com.spasinnya.mentoring.data.repository

import com.spasinnya.mentoring.data.mapper.toDomain
import com.spasinnya.mentoring.data.model.CredentialsApiRequest
import com.spasinnya.mentoring.data.model.OtpCredentialsApiRequest
import com.spasinnya.mentoring.data.model.TokenApiRequest
import com.spasinnya.mentoring.data.model.TokenApiResponse
import com.spasinnya.mentoring.data.net.postFlow
import com.spasinnya.mentoring.data.storage.datastore.TokenStore
import com.spasinnya.mentoring.domain.repository.LoginRepository
import com.spasinnya.mentoring.domain.repository.LogoutRepository
import com.spasinnya.mentoring.domain.repository.OtpRepository
import com.spasinnya.mentoring.domain.repository.RegisterRepository
import com.spasinnya.mentoring.presentation.base.alsoDo
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.map

fun loginRepository(
    http: HttpClient,
    tokenStore: TokenStore
): LoginRepository = { creds ->
    http.postFlow<CredentialsApiRequest, TokenApiResponse>(
        path = "login",
        body = creds
    )
        .map(TokenApiResponse::toDomain)
        .alsoDo(tokenStore::save)
}

fun registerRepo(
    http: HttpClient,
): RegisterRepository = { creds ->
    http.postFlow<CredentialsApiRequest, String>(
        path = "register",
        body = creds
    )
}

fun otpRepo(
    http: HttpClient,
    tokenStore: TokenStore
): OtpRepository = { creds ->
    http.postFlow<OtpCredentialsApiRequest, TokenApiResponse>(
        path = "verify-otp",
        body = creds
    )
        .map(TokenApiResponse::toDomain)
        .alsoDo(tokenStore::save)
}

fun logoutRepo(
    http: HttpClient,
    tokenStore: TokenStore
): LogoutRepository = {
    val token = tokenStore.read() ?: throw Exception("Can't logout, no token")

    http.postFlow<TokenApiRequest, Unit>(
        path = "logout",
        body = TokenApiRequest(token.refreshToken)
    ).alsoDo {
        tokenStore.clear()
    }
}