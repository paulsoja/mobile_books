package com.spasinnya.mentoring.domain.mapper

import com.spasinnya.mentoring.domain.rules.DomainError
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
        is DomainError.Business -> UiErrorType.Client

        DomainError.Unknown -> UiErrorType.Unknown
    }