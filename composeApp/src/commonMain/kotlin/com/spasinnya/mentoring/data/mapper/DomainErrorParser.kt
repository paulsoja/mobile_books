package com.spasinnya.mentoring.data.mapper

import com.spasinnya.mentoring.data.model.ApiErrorResponse
import com.spasinnya.mentoring.data.model.DataError
import com.spasinnya.mentoring.domain.model.DomainError
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.serialization.JsonConvertException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.io.IOException
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

fun parseApiErrorOrNull(
    json: Json,
    raw: String?
): ApiErrorResponse? {
    if (raw.isNullOrBlank()) return null

    return runCatching {
        json.decodeFromString<ApiErrorResponse>(raw)
    }.getOrNull()
}

fun Throwable.toDataError(): DataError =
    when (this) {
        is HttpRequestTimeoutException,
        is TimeoutCancellationException -> DataError.Timeout

        is IOException -> DataError.NoInternet

        is SerializationException,
        is JsonConvertException,
        is NoTransformationFoundException -> DataError.Serialization

        else -> DataError.Unknown
    }

fun mapHttpError(
    statusCode: Int,
    apiError: ApiErrorResponse?
): DataError {
    apiError?.error?.let { error ->
        return when (error) {
            "User already exists" -> DataError.ApiBusinessError("USER_ALREADY_EXISTS")
            else -> DataError.Unknown
        }
    }

    return when (statusCode) {
        401 -> DataError.Unauthorized
        403 -> DataError.Forbidden
        404 -> DataError.NotFound
        in 400..499 -> DataError.ClientError
        in 500..599 -> DataError.ServerError
        else -> DataError.Unknown
    }
}

fun DataError.toDomainError(): DomainError =
    when (this) {
        DataError.NoInternet -> DomainError.NoInternet
        DataError.Timeout -> DomainError.Timeout

        DataError.Unauthorized -> DomainError.Unauthorized
        DataError.Forbidden -> DomainError.Forbidden
        DataError.NotFound -> DomainError.NotFound

        DataError.ClientError -> DomainError.ClientError
        DataError.ServerError -> DomainError.ServerError
        DataError.Serialization -> DomainError.Serialization
        DataError.Unknown -> DomainError.Unknown

        is DataError.ApiBusinessError -> when (code) {
            "USER_NOT_FOUND" -> DomainError.UserNotFound
            "INVALID_OTP" -> DomainError.InvalidOtp
            "EMAIL_ALREADY_EXISTS" -> DomainError.EmailAlreadyExists
            "WRONG_PASSWORD" -> DomainError.WrongPassword
            "OTP_EXPIRED" -> DomainError.OtpExpired
            "WEAK_PASSWORD" -> DomainError.WeakPassword
            "USER_ALREADY_EXISTS" -> DomainError.UserAlreadyExists
            else -> DomainError.Unknown
        }
    }