package com.spasinnya.mentoring.domain.mapper

import com.spasinnya.mentoring.domain.model.DomainError
import com.spasinnya.mentoring.presentation.model.UiErrorType

fun DomainError.toUiErrorType(): UiErrorType =
    when (this) {
        DomainError.NoInternet,
        DomainError.Timeout -> UiErrorType.NoInternet

        DomainError.ServerError -> UiErrorType.Server

        DomainError.Unauthorized,
        DomainError.Forbidden,
        DomainError.NotFound,
        DomainError.ClientError,

        DomainError.Unknown -> UiErrorType.Unknown
        DomainError.EmailAlreadyExists,
        DomainError.InvalidOtp,
        DomainError.OtpExpired,
        DomainError.Serialization,
        DomainError.UserNotFound,
        DomainError.UserAlreadyExists,
        DomainError.WeakPassword,
        DomainError.WrongPassword -> UiErrorType.Client
    }