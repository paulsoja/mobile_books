package com.spasinnya.mentoring.presentation.designsystem.composable.inputs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.spasinnya.mentoring.domain.model.Password
import com.spasinnya.mentoring.domain.model.PasswordConfirmation
import com.spasinnya.mentoring.generated.resources.Res
import com.spasinnya.mentoring.generated.resources.error_password_invalid
import com.spasinnya.mentoring.generated.resources.error_passwords_not_match
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreOutlinedTextField
import com.spasinnya.mentoring.presentation.designsystem.defaults.InputPasswordDefaults
import org.jetbrains.compose.resources.stringResource

@Composable
fun PasswordInput(
    password: Password,
    passwordError: Password.Error,
    isVisible: Boolean,
    onToggleVisibility: (Boolean) -> Unit,
    onValueChange: (Password) -> Unit,
    modifier: Modifier = Modifier,
) {
    SecretInput(
        modifier = modifier,
        value = password.value,
        errorText = passwordError.toMessage(),
        isVisible = isVisible,
        onToggleVisibility = onToggleVisibility,
        onValueChange = { newText -> onValueChange(Password.raw(newText)) }
    )
}

@Composable
fun PasswordConfirmationInput(
    confirmation: PasswordConfirmation,
    confirmationError: PasswordConfirmation.Error,
    isVisible: Boolean,
    onToggleVisibility: (Boolean) -> Unit,
    onValueChange: (PasswordConfirmation) -> Unit,
    modifier: Modifier = Modifier,
) {
    SecretInput(
        modifier = modifier,
        value = confirmation.value,
        errorText = confirmationError.toMessage(),
        isVisible = isVisible,
        onToggleVisibility = onToggleVisibility,
        onValueChange = { newText -> onValueChange(PasswordConfirmation.raw(newText)) }
    )
}

@Composable
private fun SecretInput(
    value: String,
    errorText: String,
    isVisible: Boolean,
    onToggleVisibility: (Boolean) -> Unit,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    CoreOutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        inputDefaults = InputPasswordDefaults(
            isVisible = isVisible,
            onToggleVisibility = { onToggleVisibility.invoke(isVisible) }
        ),
        errorText = errorText
    )
}

@Composable
fun Password.Error.toMessage(): String =
    when (this) {
        Password.Error.NoError -> ""
        Password.Error.Empty,
        Password.Error.TooShort,
        Password.Error.NoDigit ->
            stringResource(Res.string.error_password_invalid)
    }

@Composable
fun PasswordConfirmation.Error.toMessage(): String =
    when (this) {
        PasswordConfirmation.Error.NoError -> ""
        PasswordConfirmation.Error.Empty,
        PasswordConfirmation.Error.Mismatch ->
            stringResource(Res.string.error_passwords_not_match)
    }
