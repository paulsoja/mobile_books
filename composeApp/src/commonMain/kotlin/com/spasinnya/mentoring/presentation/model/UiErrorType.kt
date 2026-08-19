package com.spasinnya.mentoring.presentation.model

sealed class UiErrorType {
    data object None : UiErrorType()

    /** No connection / timeout */
    data object NoInternet : UiErrorType()

    /** Any 4xx-exception (except 401) */
    data object Client : UiErrorType()

    /** Any 5xx-exception */
    data object Server : UiErrorType()

    data object UserNotFound : UiErrorType()

    /** 401 - the session is gone and the user has to sign in again */
    data object SessionExpired : UiErrorType()

    data object OtpExpired : UiErrorType()

    data object Unknown : UiErrorType()
}