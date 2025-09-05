package com.spasinnya.mentoring.data.net

import com.gyanoba.inspektor.Inspektor
import com.gyanoba.inspektor.UnstableInspektorAPI
import com.spasinnya.mentoring.data.net.plugin.Connectivity
import com.spasinnya.mentoring.data.net.plugin.NetStatus
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

private const val API_HOST = "web-books-1.onrender.com"

val httpClient: HttpClient = HttpClient(CIO) {
    connectivityPlugin()
    inspektorPlugin()
    contentNegotiationPlugin()
    loggingPlugin()
    httpTimeoutPlugin()

    expectSuccess = true

    defaultRequest {
        url {
            protocol = URLProtocol.HTTPS
            host = API_HOST
        }
        header("Accept", "application/json")
        contentType(ContentType.Application.Json)
    }
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