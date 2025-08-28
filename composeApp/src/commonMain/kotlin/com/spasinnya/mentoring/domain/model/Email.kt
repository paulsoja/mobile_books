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
value class Email(val value: String) {

    companion object {
        val init: Email = Email("")

        fun of(email: String): Validated<Error, Email> {
            return when {
                email.isEmpty() -> Validated.Invalid(listOf(Error.Empty))
                isValid(email, RegexType.EMAIL) -> Validated.Valid(Email(email))
                else -> Validated.Invalid(listOf(Error.Invalid_format))
            }
        }
    }

    sealed interface Status {
        data class Valid(val email: String) : Status
        data class Failed(val error: Error) : Status
    }

    enum class Error(val message: @Composable () -> String) {
        No_error({ "" }),
        Invalid_format({ stringResource(Res.string.error_email_invalid) }),
        Empty({ stringResource(Res.string.error_email_invalid) }),
    }
}