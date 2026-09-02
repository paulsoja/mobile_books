package com.spasinnya.mentoring.data.net

import com.spasinnya.mentoring.data.mapper.toDomain
import com.spasinnya.mentoring.data.model.TokenApiRequest
import com.spasinnya.mentoring.data.model.TokenApiResponse
import com.spasinnya.mentoring.data.net.plugin.Connectivity
import com.spasinnya.mentoring.data.net.plugin.NetStatus
import com.spasinnya.mentoring.data.storage.datastore.TokenStore
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.AuthCircuitBreaker
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.plugins.plugin
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

private const val API_HOST = "web-books-1.onrender.com"
private const val REFRESH_PATH = "refresh"

private val PUBLIC_PATHS = setOf(
    "login",
    "register",
    "verify-otp",
    "request-otp",
    "reset-password",
    REFRESH_PATH,
)

private fun HttpRequestBuilder.isPublicEndpoint(): Boolean =
    url.encodedPathSegments.filter(String::isNotEmpty).joinToString("/") in PUBLIC_PATHS

expect fun platformEngine(): HttpClientEngineFactory<*>

fun createHttpClient(
    tokenStore: TokenStore,
    readLanguageTag: suspend () -> String?,
    defaultLang: String,
    onSessionExpired: suspend () -> Unit,
): HttpClient {
    val client = HttpClient(platformEngine()) {
        connectivityPlugin()
        //inspektorPlugin()
        contentNegotiationPlugin()
        loggingPlugin()
        httpTimeoutPlugin()

        //expectSuccess = true

        attachAuth(tokenStore = tokenStore, onSessionExpired = onSessionExpired)

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

        if (request.isPublicEndpoint()) {
            request.attributes.put(AuthCircuitBreaker, Unit)
        }

        execute(request)
    }

    return client
}

inline fun <reified T : HttpClientEngineConfig> HttpClientConfig<T>.connectivityPlugin() {
    install(Connectivity) {
        isOnline = { NetStatus.isOnline() }
    }
}

/*@OptIn(UnstableInspektorAPI::class)
inline fun <reified T : HttpClientEngineConfig> HttpClientConfig<T>.inspektorPlugin() {
    install(Inspektor) {
        level = com.gyanoba.inspektor.LogLevel.BODY
        sanitizeHeader { it == "Authorization" }
        //showNotifications = true
    }
}*/

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

fun HttpClientConfig<*>.attachAuth(
    tokenStore: TokenStore,
    onSessionExpired: suspend () -> Unit,
) {
    install(Auth) {
        bearer {
            sendWithoutRequest { request -> !request.isPublicEndpoint() }

            loadTokens {
                tokenStore.read()?.let {
                    BearerTokens(
                        accessToken = it.accessToken,
                        refreshToken = it.refreshToken
                    )
                }
            }
            refreshTokens {
                val stored = tokenStore.read() ?: return@refreshTokens null

                val response = runCatching {
                    client.post(REFRESH_PATH) {
                        markAsRefreshTokenRequest()
                        setBody(TokenApiRequest(stored.refreshToken))
                    }
                }.getOrNull()

                if (response?.status?.isSuccess() != true) {
                    onSessionExpired()
                    return@refreshTokens null
                }

                val token = response.body<TokenApiResponse>().toDomain()
                tokenStore.save(token)

                BearerTokens(
                    accessToken = token.accessToken,
                    refreshToken = token.refreshToken
                )
            }
        }
    }
}
