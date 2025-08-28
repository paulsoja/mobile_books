package com.spasinnya.mentoring.data.net

import com.spasinnya.mentoring.data.net.plugin.Connectivity
import com.spasinnya.mentoring.data.net.plugin.NetStatus
import io.ktor.client.HttpClient
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

private const val API_SCHEME = "https"
private const val API_HOST   = "web-books-1.onrender.com"

val httpClient: HttpClient = HttpClient(CIO) {
    install(Connectivity) {
        isOnline = { NetStatus.isOnline() }
    }
    expectSuccess = true
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            ignoreUnknownKeys = true
        })
    }
    install(Logging) {
        logger = Logger.SIMPLE
        level = LogLevel.BODY
    }
    install(HttpTimeout) {
        requestTimeoutMillis = 15_000
        connectTimeoutMillis = 15_000
        socketTimeoutMillis = 15_000
    }
    defaultRequest {
        url {
            protocol = URLProtocol.createOrDefault(API_SCHEME)
            host     = API_HOST
        }
        header("Accept", "application/json")
        contentType(ContentType.Application.Json)
    }
}