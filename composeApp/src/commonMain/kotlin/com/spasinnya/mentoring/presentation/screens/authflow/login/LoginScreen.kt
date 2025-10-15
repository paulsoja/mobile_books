package com.spasinnya.mentoring.presentation.screens.authflow.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
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
import com.spasinnya.mentoring.presentation.base.CollectEffects
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreCircularProgressIndicator
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreHorizontalDividerWithText
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreOutlinedTextField
import com.spasinnya.mentoring.presentation.designsystem.composable.CorePrimaryButton
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVertical
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVerticalLarge
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVerticalMedium
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVerticalXLarge
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextBody
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextButton
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextScreenTitle
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextSubtitle
import com.spasinnya.mentoring.presentation.designsystem.defaults.InputEmailDefaults
import com.spasinnya.mentoring.presentation.designsystem.defaults.InputPasswordDefaults
import com.spasinnya.mentoring.presentation.di.viewModelFactory
import com.spasinnya.mentoring.presentation.screens.authflow.register.ErrorState
import org.jetbrains.compose.resources.stringResource

@Composable
fun LoginScreen(
    navigateToRegister: () -> Unit,
    navigateToResetPassword: () -> Unit,
    navigateToHome: () -> Unit
) {

    val factory = remember {
        viewModelFactory { graph, handle ->
            LoginViewModel(
                loginUseCase = graph.useCases.loginUseCase,
                savedState = handle
            )
        }
    }

    val viewModel: LoginViewModel = viewModel(factory = factory)
    val state by viewModel.state.collectAsStateWithLifecycle()

    viewModel.effect.CollectEffects { effect ->
        when (effect) {
            is LoginContract.Effect.NavigateToMain -> navigateToHome.invoke()
        }
    }

    if (state.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x66F5F7FC))
                .zIndex(4f),
            contentAlignment = Alignment.Center
        ) {
            CoreCircularProgressIndicator()
        }
    }

    ErrorState(
        errorType = state.messageError,
        onAction = {
            viewModel.dispatchEvent(
                LoginContract.Event.ValidateCredentials(
                    email = state.email.value,
                    password = state.password.value
                )
            )
        },
        onDismiss = {
            viewModel.dispatchEvent(LoginContract.Event.HandleError(LoginContract.ErrorType.NoError))
        }
    )

    Column(
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
        // TODO uncomment after creating custom Text Field
//        CoreTextSubtitle(
//            text = "Email",
//            modifier = Modifier.fillMaxWidth(),
//            textAlign = TextAlign.Start,
//            style = MaterialTheme.typography.titleMedium.copy(
//                color = Color(0xFF3C4E73)
//            ),
//        )
//        CoreSpacerVerticalMedium()
        CoreOutlinedTextField(
            value = state.email.value,
            onValueChange = { viewModel.dispatchEvent(LoginContract.Event.EmailChanged(it)) },
            inputDefaults = InputEmailDefaults(),
            errorText = state.emailError.message()
        )
        CoreSpacerVertical(height = 20.dp)
        // TODO uncomment after creating custom Text Field
//        CoreTextSubtitle(
//            text = "Пароль",
//            modifier = Modifier.fillMaxWidth(),
//            textAlign = TextAlign.Start,
//            style = MaterialTheme.typography.titleMedium.copy(
//                color = Color(0xFF3C4E73)
//            ),
//        )
//        CoreSpacerVerticalMedium()
        CoreOutlinedTextField(
            value = state.password.value,
            onValueChange = { viewModel.dispatchEvent(LoginContract.Event.PasswordChanged(it)) },
            inputDefaults = InputPasswordDefaults(),
            errorText = state.passwordError.message()
        )
        Spacer(modifier = Modifier.height(24.dp))
        CorePrimaryButton(
            text = stringResource(Res.string.auth_login),
            backgroundColor = Color(0xFF3C4E73),
            iconAfter = Res.drawable.ic_arrow_right,
            iconTint = Color.White,
            onClick = {
                viewModel.dispatchEvent(
                    LoginContract.Event.ValidateCredentials(
                        email = state.email.value,
                        password = state.password.value
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
            onClick = {  },
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
}