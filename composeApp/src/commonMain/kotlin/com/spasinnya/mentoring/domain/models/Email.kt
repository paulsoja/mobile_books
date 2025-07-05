package com.spasinnya.mentoring.domain.models

import com.spasinnya.mentoring.domain.rules.RegexType
import com.spasinnya.mentoring.domain.rules.isValid
import kotlin.jvm.JvmInline

@JvmInline
value class Email private constructor(val email: String) {

    companion object {
        fun of(email: String): Status {
            return when {
                isValid(email, RegexType.EMAIL) -> Status.Valid(Email(email))
                else -> Status.Failed(Email(email))
            }
        }
    }

    sealed interface Status {
        data class Valid(val email: Email) : Status
        data class Failed(val email: Email) : Status
    }
}