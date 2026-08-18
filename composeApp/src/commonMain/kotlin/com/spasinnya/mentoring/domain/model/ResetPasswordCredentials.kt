package com.spasinnya.mentoring.domain.model

import com.spasinnya.mentoring.domain.rules.mapErrors
import com.spasinnya.mentoring.domain.rules.zip
import com.spasinnya.mentoring.presentation.base.Validated

data class ResetPasswordCredentials(
    val email: Email.Valid,
    val otp: OtpCode.Valid,
    val password: Password.Valid,
) {

    sealed class ResetPasswordError {
        data class InvalidEmail(val error: Email.Error) : ResetPasswordError()
        data class InvalidOtp(val error: OtpCode.Error) : ResetPasswordError()
        data class InvalidPassword(val error: Password.Error) : ResetPasswordError()
        data class InvalidConfirmation(val error: PasswordConfirmation.Error) : ResetPasswordError()
    }

    companion object {
        fun of(
            email: Email,
            otp: OtpCode,
            password: Password,
            confirmation: PasswordConfirmation,
        ): Validated<ResetPasswordError, ResetPasswordCredentials> {
            val emailValidated: Validated<ResetPasswordError, Email.Valid> =
                email.validate().mapErrors(transform = ResetPasswordError::InvalidEmail)

            val otpValidated: Validated<ResetPasswordError, OtpCode.Valid> =
                otp.validate().mapErrors(transform = ResetPasswordError::InvalidOtp)

            val passwordValidated: Validated<ResetPasswordError, Password.Valid> =
                password.validate().mapErrors(transform = ResetPasswordError::InvalidPassword)

            val confirmationValidated: Validated<ResetPasswordError, PasswordConfirmation.Valid> =
                confirmation.validate(password).mapErrors(transform = ResetPasswordError::InvalidConfirmation)

            return emailValidated
                .zip(otpValidated, ::Pair)
                .zip(passwordValidated) { (validEmail, validOtp), validPassword ->
                    ResetPasswordCredentials(email = validEmail, otp = validOtp, password = validPassword)
                }
                .zip(confirmationValidated) { credentials, _ -> credentials }
        }
    }
}
