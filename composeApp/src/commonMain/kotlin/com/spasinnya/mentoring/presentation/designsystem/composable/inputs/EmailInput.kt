package com.spasinnya.mentoring.presentation.designsystem.composable.inputs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.spasinnya.mentoring.domain.model.Email
import com.spasinnya.mentoring.generated.resources.Res
import com.spasinnya.mentoring.generated.resources.error_email_invalid
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreOutlinedTextField
import com.spasinnya.mentoring.presentation.designsystem.defaults.InputEmailDefaults
import org.jetbrains.compose.resources.stringResource

@Composable
fun EmailInput(
    email: Email,
    emailError: Email.Error,
    defaults: InputEmailDefaults,
    onValueChange: (Email) -> Unit,
    modifier: Modifier = Modifier,
) {
    CoreOutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = email.value,
        onValueChange = { newText ->
            onValueChange(Email.raw(newText))
        },
        inputDefaults = defaults,
        errorText = emailError.toMessage()
    )
}

@Composable
fun Email.Error.toMessage(): String =
    when (this) {
        Email.Error.NoError -> ""
        Email.Error.Empty -> stringResource(Res.string.error_email_invalid)
        Email.Error.TooLong -> stringResource(Res.string.error_email_invalid)
        Email.Error.InvalidFormat -> stringResource(Res.string.error_email_invalid)
    }