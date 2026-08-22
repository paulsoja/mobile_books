package com.spasinnya.mentoring.domain.model

import com.spasinnya.mentoring.presentation.base.Validated
import kotlin.jvm.JvmInline

@JvmInline
value class PasswordConfirmation private constructor(val value: String) {

    companion object {
        val init = PasswordConfirmation("")

        fun raw(value: String): PasswordConfirmation = PasswordConfirmation(value)
    }

    fun validate(password: Password): Validated<Error, Valid> =
        when {
            value.isEmpty() -> Validated.Invalid(Error.Empty)
            value != password.value -> Validated.Invalid(Error.Mismatch)
            else -> Validated.Valid(Valid(value))
        }

    @JvmInline
    value class Valid internal constructor(val value: String)

    enum class Error {
        NoError,
        Empty,
        Mismatch,
    }
}
