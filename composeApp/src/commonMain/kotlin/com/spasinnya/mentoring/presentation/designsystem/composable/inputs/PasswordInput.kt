package com.spasinnya.mentoring.presentation.designsystem.composable.inputs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.spasinnya.mentoring.domain.model.Password
import com.spasinnya.mentoring.generated.resources.Res
import com.spasinnya.mentoring.generated.resources.error_password_invalid
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
    val inputDefaults = InputPasswordDefaults(
        isVisible = isVisible,
        onToggleVisibility = { onToggleVisibility.invoke(isVisible) }
    )

    CoreOutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = password.value,
        onValueChange = { newText ->
            onValueChange(Password.raw(newText))
        },
        inputDefaults = inputDefaults,
        errorText = passwordError.toMessage()
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