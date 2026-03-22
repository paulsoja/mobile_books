package com.spasinnya.mentoring.data.net

import com.gyanoba.inspektor.Inspektor
import com.gyanoba.inspektor.UnstableInspektorAPI
import com.spasinnya.mentoring.data.net.plugin.Connectivity
import com.spasinnya.mentoring.data.net.plugin.NetStatus
import com.spasinnya.mentoring.data.storage.datastore.TokenStore
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.plugins.plugin
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

private const val API_HOST = "web-books-1.onrender.com"

expect fun platformEngine(): HttpClientEngineFactory<*>

fun createHttpClient(
    tokenStore: TokenStore,
    readLanguageTag: suspend () -> String?,
    defaultLang: String,
): HttpClient {
    val client = HttpClient(platformEngine()) {
        connectivityPlugin()
        inspektorPlugin()
        contentNegotiationPlugin()
        loggingPlugin()
        httpTimeoutPlugin()

        //expectSuccess = true

        attachAuth(tokenStore)

        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
                host = API_HOST
            }
            header("Accept", "application/json")
            contentType(ContentType.Application.Json)
        }
    }

    client.plugin(HttpSend).intercept { request ->
        val tag = readLanguageTag()?.trim()?.ifBlank { null } ?: defaultLang

        request.headers.remove(HttpHeaders.AcceptLanguage)
        request.headers.append(HttpHeaders.AcceptLanguage, tag)
        execute(request)
    }

    return client
}

inline fun <reified T : HttpClientEngineConfig> HttpClientConfig<T>.connectivityPlugin() {
    install(Connectivity) {
        isOnline = { NetStatus.isOnline() }
    }
}

@OptIn(UnstableInspektorAPI::class)
inline fun <reified T : HttpClientEngineConfig> HttpClientConfig<T>.inspektorPlugin() {
    install(Inspektor) {
        level = com.gyanoba.inspektor.LogLevel.BODY
        sanitizeHeader { it == "Authorization" }
        showNotifications = true
    }
}

inline fun <reified T : HttpClientEngineConfig> HttpClientConfig<T>.contentNegotiationPlugin() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            ignoreUnknownKeys = true
        })
    }
}

inline fun <reified T : HttpClientEngineConfig> HttpClientConfig<T>.loggingPlugin() {
    install(Logging) {
        logger = Logger.SIMPLE
        level = LogLevel.BODY
    }
}

inline fun <reified T : HttpClientEngineConfig> HttpClientConfig<T>.httpTimeoutPlugin() {
    install(HttpTimeout) {
        requestTimeoutMillis = 15_000
        connectTimeoutMillis = 15_000
        socketTimeoutMillis = 15_000
    }
}

fun HttpClientConfig<*>.attachAuth(tokenStore: TokenStore) {
    install(Auth) {
        bearer {
            loadTokens {
                tokenStore.read()?.let {
                    BearerTokens(
                        accessToken = it.accessToken,
                        refreshToken = it.refreshToken
                    )
                }
            }
            refreshTokens {
                val new = /* refresh flow/use-case */ tokenStore.read()
                new?.let { BearerTokens(it.accessToken, it.refreshToken) }
            }
        }
    }
}
