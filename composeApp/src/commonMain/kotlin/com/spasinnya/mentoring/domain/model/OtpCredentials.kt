package com.spasinnya.mentoring.domain.model

import com.spasinnya.mentoring.domain.rules.mapErrors
import com.spasinnya.mentoring.domain.rules.zip
import com.spasinnya.mentoring.presentation.base.Validated

data class OtpCredentials(
    val email: Email.Valid,
    val otp: OtpCode.Valid,
) {

    sealed class OtpCredentialsError {
        data class Email(val error: Email.Error) : OtpCredentialsError()
        data class Otp(val error: OtpCode.Error) : OtpCredentialsError()
    }

    companion object {
        fun of(email: Email, otp: OtpCode): Validated<OtpCredentialsError, OtpCredentials> {
            val emailValidated: Validated<OtpCredentialsError, Email.Valid> =
                email.validate().mapErrors(transform = OtpCredentialsError::Email)

            val otpValidated: Validated<OtpCredentialsError, OtpCode.Valid> =
                otp.validate().mapErrors(transform = OtpCredentialsError::Otp)

            return emailValidated.zip(other = otpValidated, combine = ::OtpCredentials)
        }

        fun of(email: String, otp: String): Validated<OtpCredentialsError, OtpCredentials> =
            of(email = Email.raw(email), otp = OtpCode.raw(otp))
    }
}
