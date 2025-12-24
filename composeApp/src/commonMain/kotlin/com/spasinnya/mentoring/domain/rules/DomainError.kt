package com.spasinnya.mentoring.domain.rules

import com.spasinnya.mentoring.presentation.base.Validated

sealed class DomainError {
    data object NoInternet : DomainError()
    data object Timeout : DomainError()

    data object Unauthorized : DomainError()   // 401
    data object Forbidden : DomainError()      // 403
    data object NotFound : DomainError()       // 404
    data object ClientError : DomainError()    // other 4xx
    data object ServerError : DomainError()    // 5xx

    data class Business(
        val code: String,
        val message: String? = null
    ) : DomainError()

    data object Unknown : DomainError()
}

typealias DomainResult<T> = Validated<DomainError, T>