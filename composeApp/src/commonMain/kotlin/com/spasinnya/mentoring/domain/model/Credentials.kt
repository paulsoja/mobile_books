package com.spasinnya.mentoring.domain.model

import com.spasinnya.mentoring.domain.rules.Validated
import com.spasinnya.mentoring.domain.rules.mapErrors
import com.spasinnya.mentoring.domain.rules.zip

data class Credentials(
    val email: Email,
    val password: Password
) {
    companion object {
        fun of(email: String, password: String): Validated<CredentialsError, Credentials> {
            val emailValidated = Email.of(email)
                .mapErrors { CredentialsError.Email(it) }
            val passwordValidated = Password.of(password)
                .mapErrors { CredentialsError.Password(it) }

            return emailValidated.zip(passwordValidated) { e, p ->
                Credentials(e, p)
            }
        }
    }

    sealed class CredentialsError {
        data class Email(val error: Email.Error) : CredentialsError()
        data class Password(val error: Password.Error) : CredentialsError()
    }
}
