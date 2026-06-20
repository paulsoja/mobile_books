package com.spasinnya.mentoring.domain.model

import androidx.compose.runtime.Composable
import com.spasinnya.mentoring.domain.rules.RegexType
import com.spasinnya.mentoring.domain.rules.isValid
import com.spasinnya.mentoring.generated.resources.Res
import com.spasinnya.mentoring.generated.resources.error_otp_invalid
import com.spasinnya.mentoring.generated.resources.error_otp_not_filled
import com.spasinnya.mentoring.presentation.base.Validated
import org.jetbrains.compose.resources.stringResource
import kotlin.jvm.JvmInline

@JvmInline
value class OtpCode private constructor(val value: String) {

    companion object {
        val init: OtpCode = OtpCode("")
        fun raw(value: String): OtpCode = OtpCode(value)
        fun of(code: String): Validated<Error, Valid> = raw(code).validate()
    }

    fun validate(): Validated<Error, Valid> {
        val code = value

        return when {
            code.length != 4 -> Validated.Invalid(Error.NotFilled)
            isValid(code, RegexType.OTP_CODE) -> Validated.Valid(Valid(code))
            else -> Validated.Invalid(Error.InvalidFormat)
        }
    }

    @JvmInline
    value class Valid internal constructor(val value: String)

    enum class Error {
        NoError,
        InvalidFormat,
        NotFilled,
    }
}

@Composable
fun OtpCode.Error.toMessage(): String =
    when (this) {
        OtpCode.Error.NoError -> ""
        OtpCode.Error.InvalidFormat -> stringResource(Res.string.error_otp_invalid)
        OtpCode.Error.NotFilled -> stringResource(Res.string.error_otp_not_filled)
    }
