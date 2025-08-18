package com.spasinnya.mentoring.domain.model

import com.spasinnya.mentoring.domain.rules.RegexType
import com.spasinnya.mentoring.domain.rules.Validated
import com.spasinnya.mentoring.domain.rules.isValid
import kotlin.jvm.JvmInline

@JvmInline
value class Password(val value: String) {

    companion object {
        val init = Password("")

        fun of(password: String): Validated<Error, Password> {
            return when {
                password.isEmpty() -> Validated.Invalid(listOf(Error.EMPTY))
                password.length < 8 -> Validated.Invalid(listOf(Error.TOO_SHORT))
                isValid(password, RegexType.PASSWORD) -> Validated.Valid(Password(password))
                else -> Validated.Invalid(listOf(Error.INVALID_FORMAT))
            }
        }
    }

    enum class Error {
        TOO_SHORT,
        INVALID_FORMAT,
        EMPTY,
    }
}