package com.spasinnya.mentoring.domain.model

import com.spasinnya.mentoring.domain.rules.RegexType
import com.spasinnya.mentoring.domain.rules.Validated
import com.spasinnya.mentoring.domain.rules.isValid
import kotlin.jvm.JvmInline

@JvmInline
value class Email(val value: String) {

    companion object {
        val init: Email = Email("")

        fun of(email: String): Validated<Error, Email> {
            return if (isValid(email, RegexType.EMAIL))
                Validated.Valid(Email(email))
            else
                Validated.Invalid(listOf(Error.INVALID_FORMAT))
        }
    }

    sealed interface Status {
        data class Valid(val email: String) : Status
        data class Failed(val error: Error) : Status
    }

    enum class Error {
        INVALID_FORMAT,
        EMPTY,
    }
}