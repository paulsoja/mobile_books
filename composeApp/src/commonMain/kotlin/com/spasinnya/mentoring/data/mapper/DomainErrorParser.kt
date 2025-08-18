package com.spasinnya.mentoring.data.mapper

import com.spasinnya.mentoring.data.model.AppError
import com.spasinnya.mentoring.data.model.ErrorEnvelope
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.io.IOException
import kotlinx.serialization.json.Json
import kotlin.coroutines.cancellation.CancellationException

private val jsonLoose = Json { ignoreUnknownKeys = true }

private suspend fun parseDomainError(r: HttpResponse): AppError.Domain? =
    runCatching {
        val text = r.bodyAsText()
        val e = jsonLoose.decodeFromString(ErrorEnvelope.serializer(), text)
        AppError.Domain(
            code = e.code ?: r.status.value,
            message_ = e.message,
            statusCode = e.statusCode ?: "UNKNOWN"
        )
    }.getOrNull()

suspend fun mapToAppError(t: Throwable): AppError = when (t) {
    is CancellationException -> throw t

    // No network
    is IOException,
    is ConnectTimeoutException,
    is SocketTimeoutException,
    is HttpRequestTimeoutException -> AppError.NoConnection

    // HTTP != 2xx
    is ClientRequestException, // 4xx
    is ServerResponseException, // 5xx
    is RedirectResponseException -> {
        val resp = t.response
        parseDomainError(resp) ?: AppError.Domain(
            code = resp.status.value,
            message_ = t.message,
            statusCode = "HTTP_${resp.status.value}"
        )
    }

    else -> AppError.Unexpected(t)
}