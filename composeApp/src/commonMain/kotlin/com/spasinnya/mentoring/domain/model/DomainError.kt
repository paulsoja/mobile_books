package com.spasinnya.mentoring.domain.model

import com.spasinnya.mentoring.presentation.base.Validated

sealed interface DomainError {
    data object NoInternet : DomainError
    data object Timeout : DomainError

    data object Unauthorized : DomainError
    data object Forbidden : DomainError
    data object NotFound : DomainError

    data object UserNotFound : DomainError
    data object InvalidCredentials : DomainError
    data object InvalidOtp : DomainError
    data object EmailAlreadyExists : DomainError
    data object WrongPassword : DomainError
    data object OtpExpired : DomainError
    data object WeakPassword : DomainError
    data object UserAlreadyExists : DomainError

    data object ClientError : DomainError
    data object ServerError : DomainError
    data object Serialization : DomainError

    data object FileNotFound : DomainError
    data object FileRead : DomainError
    data object EmptyContent : DomainError
    data object InvalidBookFormat : DomainError
    data object InvalidWeekFormat : DomainError
    data object InvalidLessonFormat : DomainError

    data object Unknown : DomainError
}

typealias DomainResult<T> = Validated<DomainError, T>