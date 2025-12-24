package com.spasinnya.mentoring.presentation.screens.authflow.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import books.composeapp.generated.resources.Res
import books.composeapp.generated.resources.auth_forgot_password
import books.composeapp.generated.resources.auth_here_account_login
import books.composeapp.generated.resources.auth_login
import books.composeapp.generated.resources.auth_login_hello
import books.composeapp.generated.resources.auth_login_with_google
import books.composeapp.generated.resources.auth_no_account
import books.composeapp.generated.resources.auth_or
import books.composeapp.generated.resources.auth_register
import books.composeapp.generated.resources.ic_arrow_right
import books.composeapp.generated.resources.ic_google
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreHorizontalDividerWithText
import com.spasinnya.mentoring.presentation.designsystem.composable.CorePrimaryButton
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVertical
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVerticalLarge
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVerticalMedium
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVerticalXLarge
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextBody
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextButton
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextScreenTitle
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextSubtitle
import com.spasinnya.mentoring.presentation.designsystem.composable.inputs.EmailInput
import com.spasinnya.mentoring.presentation.designsystem.composable.inputs.PasswordInput
import com.spasinnya.mentoring.presentation.designsystem.defaults.InputEmailDefaults
import org.jetbrains.compose.resources.stringResource

@Composable
fun LoginContent(
    state: LoginContract.State,
    onEvent: (LoginContract.Event) -> Unit,
    navigateToRegister: () -> Unit,
    navigateToResetPassword: () -> Unit,
) = Column(
    modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
    horizontalAlignment = Alignment.CenterHorizontally
) {
    CoreSpacerVerticalXLarge()
    CoreTextScreenTitle(
        text = stringResource(Res.string.auth_login_hello),
        modifier = Modifier.fillMaxWidth(),
    )
    CoreSpacerVerticalMedium()
    CoreTextSubtitle(
        text = stringResource(Res.string.auth_here_account_login),
        modifier = Modifier.fillMaxWidth(),
    )
    CoreSpacerVerticalLarge()
    EmailInput(
        email = state.email,
        emailError = state.emailError,
        defaults = InputEmailDefaults(),
        onValueChange = { onEvent.invoke(LoginContract.Event.EmailChanged(it)) }
    )
    CoreSpacerVertical(height = 20.dp)
    PasswordInput(
        password = state.password,
        passwordError = state.passwordError,
        isVisible = state.isPasswordVisible,
        onToggleVisibility = { onEvent(LoginContract.Event.PasswordVisibilityToggled(it)) },
        onValueChange = { onEvent(LoginContract.Event.PasswordChanged(it)) }
    )
    Spacer(modifier = Modifier.height(24.dp))
    CorePrimaryButton(
        text = stringResource(Res.string.auth_login),
        backgroundColor = Color(0xFF3C4E73),
        iconAfter = Res.drawable.ic_arrow_right,
        iconTint = Color.White,
        onClick = {
            onEvent(
                LoginContract.Event.LoginClicked(
                    email = state.email,
                    password = state.password
                )
            )
        },
    )

    CoreSpacerVerticalMedium()

    CoreTextButton(text = stringResource(Res.string.auth_forgot_password)) {
        navigateToResetPassword.invoke()
    }

    CoreHorizontalDividerWithText(text = stringResource(Res.string.auth_or))

    CoreSpacerVerticalLarge()

    CorePrimaryButton(
        text = stringResource(Res.string.auth_login_with_google),
        backgroundColor = Color.White,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color(0xFF3C4E73)
        ),
        border = BorderStroke(width = 0.dp, color = Color.Transparent),
        onClick = { },
        iconBefore = Res.drawable.ic_google,
        iconTint = Color.Unspecified
    )

    Column(
        modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CoreTextBody(stringResource(Res.string.auth_no_account))
        CoreTextButton(text = stringResource(Res.string.auth_register), onClick = navigateToRegister)
    }
}