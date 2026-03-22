package com.spasinnya.mentoring.data.model

import com.spasinnya.mentoring.presentation.base.Validated

sealed interface DataError {
    data object NoInternet : DataError
    data object Timeout : DataError

    data object Unauthorized : DataError
    data object Forbidden : DataError
    data object NotFound : DataError
    data object ClientError : DataError
    data object ServerError : DataError
    data object Serialization : DataError

    data class ApiBusinessError(
        val code: String,
        val message: String? = null,
        val error: String? = null
    ) : DataError

    data object Unknown : DataError
}

typealias DataResult<T> = Validated<DataError, T>