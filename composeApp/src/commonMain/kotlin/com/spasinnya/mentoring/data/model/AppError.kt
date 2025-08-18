package com.spasinnya.mentoring.data.model

sealed class AppError : Exception() {
    data object NoConnection : AppError()
    data class Domain(
        val code: Int,
        val message_: String?,
        val statusCode: String
    ) : AppError() {
        override val message: String? get() = message_ ?: statusCode
    }
    data class Unexpected(val cause_: Throwable) : AppError() {
        override val message: String? get() = cause_.message
        override val cause: Throwable
            get() = cause_
    }
}