package com.spasinnya.mentoring.domain.model

import com.spasinnya.mentoring.presentation.base.Validated
import kotlin.jvm.JvmInline

@JvmInline
value class Password private constructor(val value: String) {

    companion object {
        val init = Password("")

        fun raw(value: String): Password = Password(value)
        fun of(value: String): Validated<Error, Valid> = raw(value).validate()
    }

    fun validate(): Validated<Error, Valid> {
        val pwd = value

        return when {
            pwd.isEmpty() -> Validated.Invalid(listOf(Error.Empty))
            pwd.length < 8 -> Validated.Invalid(listOf(Error.TooShort))
            !pwd.any { it.isDigit() } -> Validated.Invalid(listOf(Error.NoDigit))
            else -> Validated.Valid(Valid(pwd))
        }
    }

    @JvmInline
    value class Valid internal constructor(val value: String)

    enum class Error {
        NoError,
        Empty,
        TooShort,
        NoDigit,
    }
}