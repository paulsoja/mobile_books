package com.spasinnya.mentoring.data.mapper

import com.spasinnya.mentoring.domain.rules.DomainError

fun Throwable.toDomainError(): DomainError =
    when (this) {
        is io.ktor.client.plugins.ClientRequestException -> {
            val code = response.status.value
            when (code) {
                401 -> DomainError.Unauthorized
                403 -> DomainError.Forbidden
                404 -> DomainError.NotFound
                in 400..499 -> DomainError.ClientError
                in 500..599 -> DomainError.ServerError
                else -> DomainError.Unknown
            }
        }

        is io.ktor.client.plugins.ServerResponseException -> DomainError.ServerError
        else -> DomainError.Unknown
    }