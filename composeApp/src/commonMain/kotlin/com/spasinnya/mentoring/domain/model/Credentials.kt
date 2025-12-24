package com.spasinnya.mentoring.domain.model

import com.spasinnya.mentoring.domain.rules.mapErrors
import com.spasinnya.mentoring.domain.rules.zip
import com.spasinnya.mentoring.presentation.base.Validated

data class Credentials(
    val email: Email.Valid,
    val password: Password.Valid,
) {

    sealed class CredentialsError {
        data class Email(val error: Email.Error) : CredentialsError()
        data class Password(val error: Password.Error) : CredentialsError()
    }

    companion object {
        fun of(
            email: Email,
            password: Password
        ): Validated<CredentialsError, Credentials> {
            val emailValidated: Validated<CredentialsError, Email.Valid> =
                email.validate().mapErrors(transform = CredentialsError::Email)

            val passwordValidated: Validated<CredentialsError, Password.Valid> =
                password.validate().mapErrors(transform = CredentialsError::Password)

            return emailValidated.zip(other = passwordValidated, combine = ::Credentials)
        }

        fun of(
            email: String,
            password: String
        ): Validated<CredentialsError, Credentials> =
            of(email = Email.raw(email), password = Password.raw(password))
    }
}

