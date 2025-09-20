package com.spasinnya.mentoring.data.mapper

import com.spasinnya.mentoring.data.model.AppError
import com.spasinnya.mentoring.data.model.ErrorEnvelope
import com.spasinnya.mentoring.data.net.plugin.NoInternetException
import io.github.aakira.napier.Napier
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
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

suspend fun mapToAppError(t: Throwable): AppError = when {
    t is CancellationException -> throw t

    t is NoInternetException -> AppError.NoConnection

    t is ResponseException -> {
        val resp = t.response

        parseDomainError(resp) ?: AppError.Domain(
            code = resp.status.value,
            message_ = t.message,
            statusCode = resp.body<String?>().orEmpty()
        )
    }

    else -> AppError.Unexpected(t)
}.also { Napier.d("mapToAppError: $it") }