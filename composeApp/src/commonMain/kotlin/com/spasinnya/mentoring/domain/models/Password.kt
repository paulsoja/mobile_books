package com.spasinnya.mentoring.domain.models

import com.spasinnya.mentoring.domain.rules.RegexType
import com.spasinnya.mentoring.domain.rules.isValid
import kotlin.jvm.JvmInline

@JvmInline
value class Password private constructor(val value: String) {

    companion object {
        fun of(password: String): Status {
            return when {
                isValid(password, RegexType.PASSWORD) -> Status.Valid(Password(password))
                else -> Status.Failed(Password(password))
            }
        }
    }

    sealed interface Status {
        data class Valid(val password: Password) : Status
        data class Failed(val password: Password) : Status
    }
}