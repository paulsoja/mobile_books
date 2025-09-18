package com.spasinnya.mentoring.domain.model

import com.spasinnya.mentoring.domain.rules.Validated
import com.spasinnya.mentoring.domain.rules.mapErrors
import com.spasinnya.mentoring.domain.rules.zip

data class OtpCredentials(
    val email: Email,
    val code: OtpCode
) {

    companion object {
        fun of(email: String, code: String): Validated<OtpCredentialsError, OtpCredentials> {
            val emailValidated = Email.of(email)
                .mapErrors { OtpCredentialsError.Email(it) }
            val codeValidated = OtpCode.of(code)
                .mapErrors { OtpCredentialsError.Code(it) }

            return emailValidated.zip(codeValidated) { e, c ->
                OtpCredentials(e, c)
            }
        }
    }

    sealed class OtpCredentialsError {
        data class Email(val error: Email.Error) : OtpCredentialsError()
        data class Code(val error: OtpCode.Error) : OtpCredentialsError()
    }
}
