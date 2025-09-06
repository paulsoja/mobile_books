package com.spasinnya.mentoring.domain.model

import androidx.compose.runtime.Composable
import books.composeapp.generated.resources.Res
import books.composeapp.generated.resources.error_password_invalid
import com.spasinnya.mentoring.domain.rules.RegexType
import com.spasinnya.mentoring.domain.rules.Validated
import com.spasinnya.mentoring.domain.rules.isValid
import org.jetbrains.compose.resources.stringResource
import kotlin.jvm.JvmInline

@JvmInline
value class Password(val value: String) {

    companion object {
        val init = Password("")

        fun of(password: String): Validated<Error, Password> {
            return when {
                password.isEmpty() -> Validated.Invalid(listOf(Error.Empty))
                password.length < 8 -> Validated.Invalid(listOf(Error.Too_short))
                isValid(password, RegexType.PASSWORD) -> Validated.Valid(Password(password))
                else -> Validated.Invalid(listOf(Error.Invalid_format))
            }
        }
    }

    enum class Error(val message: @Composable () -> String) {
        No_error({ "" }),
        Too_short({ stringResource(Res.string.error_password_invalid) }),
        Invalid_format({ stringResource(Res.string.error_password_invalid) }),
        Empty({ stringResource(Res.string.error_password_invalid) }),
    }
}