package com.spasinnya.mentoring.domain.model

import androidx.compose.runtime.Composable
import books.composeapp.generated.resources.Res
import books.composeapp.generated.resources.error_email_invalid
import com.spasinnya.mentoring.domain.rules.RegexType
import com.spasinnya.mentoring.domain.rules.Validated
import com.spasinnya.mentoring.domain.rules.isValid
import org.jetbrains.compose.resources.stringResource
import kotlin.jvm.JvmInline

@JvmInline
value class OtpCode(val value: String) {

    companion object {
        val init: OtpCode = OtpCode("")

        fun of(code: String): Validated<Error, OtpCode> {
            return when {
                code.length != 4 -> Validated.Invalid(listOf(Error.Not_filled))
                isValid(code, RegexType.OTP_CODE) -> Validated.Valid(OtpCode(code))
                else -> Validated.Invalid(listOf(Error.Invalid_format))
            }
        }
    }

    enum class Error(val message: @Composable () -> String) {
        No_error({ "" }),
        Invalid_format({ stringResource(Res.string.error_email_invalid) }),
        Not_filled({ stringResource(Res.string.error_email_invalid) }),
    }
}
