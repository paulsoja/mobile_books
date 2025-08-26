package com.spasinnya.mentoring.presentation.screens.authflow.register

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import books.composeapp.generated.resources.Res
import books.composeapp.generated.resources.auth_already_have_account
import books.composeapp.generated.resources.auth_create_account
import books.composeapp.generated.resources.auth_email
import books.composeapp.generated.resources.auth_login
import books.composeapp.generated.resources.auth_or
import books.composeapp.generated.resources.auth_password
import books.composeapp.generated.resources.auth_register
import books.composeapp.generated.resources.auth_register_hello
import books.composeapp.generated.resources.auth_register_with_google
import books.composeapp.generated.resources.ic_arrow_right
import books.composeapp.generated.resources.ic_google
import books.composeapp.generated.resources.ic_warning
import com.spasinnya.mentoring.domain.model.UiErrorType
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreAlertDialog
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreHorizontalDividerWithText
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreOutlinedTextField
import com.spasinnya.mentoring.presentation.designsystem.composable.CorePrimaryButton
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVertical
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVerticalLarge
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVerticalMedium
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVerticalSmall
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVerticalXLarge
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextBody
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextButton
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextScreenTitle
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextSubtitle
import com.spasinnya.mentoring.presentation.designsystem.defaults.InputEmailDefaults
import com.spasinnya.mentoring.presentation.designsystem.defaults.InputPasswordDefaults
import com.spasinnya.mentoring.presentation.di.viewmodelfactory.createRegisterViewModel
import org.jetbrains.compose.resources.stringResource

@Composable
fun RegisterScreen(
    navigateToLogin: () -> Unit,
    navigateToOtp: () -> Unit
) {

    val viewModel: RegisterViewModel = viewModel(factory = createRegisterViewModel)
    val state by viewModel.state.collectAsStateWithLifecycle()

    state.messageError?.let {
        ShowAlertDialog(
            errorType = it,
            showDialog = state.messageError != null,
            onAction = {
                viewModel.dispatchEvent(
                    RegisterContract.Event.ValidateCredentials(
                        email = state.email.value,
                        password = state.password.value
                    )
                )
            }
        )
    }

    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CoreSpacerVerticalXLarge()

        CoreTextScreenTitle(text = stringResource(Res.string.auth_register_hello))
        CoreSpacerVerticalMedium()

        CoreTextSubtitle(
            text = stringResource(Res.string.auth_create_account),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
        )
        CoreSpacerVertical(height = 20.dp)
        CoreTextSubtitle(
            text = stringResource(Res.string.auth_email),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.titleSmall.copy(
                color = Color(0xFF3C4E73)
            ),
        )
        CoreSpacerVerticalSmall()
        CoreOutlinedTextField(
            value = state.email.value,
            onValueChange = { viewModel.dispatchEvent(RegisterContract.Event.EmailChanged(it)) },
            inputDefaults = InputEmailDefaults(),
            errorText = state.emailError.message()
        )

        CoreSpacerVerticalMedium()
        CoreTextSubtitle(
            text = stringResource(Res.string.auth_password),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.titleSmall.copy(
                color = Color(0xFF3C4E73)
            ),
        )
        CoreSpacerVerticalSmall()

        CoreOutlinedTextField(
            value = state.password.value,
            onValueChange = { viewModel.dispatchEvent(RegisterContract.Event.PasswordChanged(it)) },
            inputDefaults = InputPasswordDefaults(),
            errorText = state.emailError.message()
        )

        Spacer(modifier = Modifier.height(24.dp))

        CorePrimaryButton(
            backgroundColor = Color(0xFF3C4E73),
            text = stringResource(Res.string.auth_register),
            iconAfter = Res.drawable.ic_arrow_right,
            iconTint = Color.White,
            onClick = {
                viewModel.dispatchEvent(
                    RegisterContract.Event.ValidateCredentials(
                        email = state.email.value,
                        password = state.password.value
                    )
                )
            },
        )

        CoreHorizontalDividerWithText(stringResource(Res.string.auth_or))

        CoreSpacerVerticalLarge()

        CorePrimaryButton(
            text = stringResource(Res.string.auth_register_with_google),
            backgroundColor = Color.White,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color(0xFF3C4E73)
            ),
            border = BorderStroke(width = 0.dp, color = Color.Transparent),
            onClick = {  },
            iconBefore = Res.drawable.ic_google,
            iconTint = Color.Unspecified
        )

        Column(
            modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CoreTextBody(text = stringResource(Res.string.auth_already_have_account))
            CoreTextButton(text = stringResource(Res.string.auth_login), onClick = navigateToLogin)
        }
    }
}

@Composable
fun ShowAlertDialog(
    errorType: UiErrorType,
    showDialog: Boolean,
    onAction: () -> Unit
) {
    when {
        showDialog -> {
            CoreAlertDialog(
                onDismissRequest = { onAction.invoke() },
                onConfirmation = {
                    onAction.invoke()
                },
                dialogTitle = errorType.title(),
                dialogText = errorType.message(),
                icon = Res.drawable.ic_warning
            )
        }
    }
}