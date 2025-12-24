package com.spasinnya.mentoring.domain.model

import com.spasinnya.mentoring.presentation.base.Validated
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@JvmInline
value class Email private constructor(val value: String) {

    companion object {
        val init = Email("")
        fun raw(value: String): Email = Email(value)
        fun of(value: String): Validated<Error, Valid> = raw(value).validate()
    }

    fun validate(): Validated<Error, Valid> {
        val text = value.trim()

        return when {
            text.isEmpty() -> Validated.Invalid(listOf(Error.Empty))
            text.length > 254 -> Validated.Invalid(listOf(Error.TooLong))
            !isValidEmail(text) -> Validated.Invalid(listOf(Error.InvalidFormat))
            else -> Validated.Valid(Valid(text))
        }
    }

    @JvmInline
    @Serializable
    value class Valid internal constructor(val value: String)

    enum class Error {
        NoError,
        Empty,
        TooLong,
        InvalidFormat,
    }
}

private fun isValidEmail(email: String): Boolean {
    val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    return emailRegex.matches(email)
}