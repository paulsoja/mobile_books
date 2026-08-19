package com.spasinnya.mentoring.domain.mapper

import com.spasinnya.mentoring.domain.model.DomainError
import com.spasinnya.mentoring.presentation.model.UiErrorType

fun DomainError.toUiErrorType(): UiErrorType =
    when (this) {
        DomainError.NoInternet,
        DomainError.Timeout -> UiErrorType.NoInternet

        DomainError.ServerError -> UiErrorType.Server

        DomainError.Unauthorized -> UiErrorType.SessionExpired

        DomainError.Forbidden,
        DomainError.NotFound,
        DomainError.ClientError,

        DomainError.Unknown -> UiErrorType.Unknown

        DomainError.UserNotFound -> UiErrorType.UserNotFound

        DomainError.InvalidOtp,
        DomainError.OtpExpired -> UiErrorType.OtpExpired

        DomainError.EmailAlreadyExists,
        DomainError.Serialization,
        DomainError.UserAlreadyExists,
        DomainError.WeakPassword,
        DomainError.WrongPassword -> UiErrorType.Client

        DomainError.EmptyContent -> TODO()
        DomainError.FileNotFound -> TODO()
        DomainError.FileRead -> TODO()
        DomainError.InvalidBookFormat -> TODO()
        DomainError.InvalidLessonFormat -> TODO()
        DomainError.InvalidWeekFormat -> TODO()
    }