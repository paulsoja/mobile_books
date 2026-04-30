package com.spasinnya.mentoring.presentation.screens.authflow.newpassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import books.composeapp.generated.resources.Res
import books.composeapp.generated.resources.ic_check
import books.composeapp.generated.resources.ic_logo
import com.spasinnya.mentoring.presentation.base.rememberScreenModel
import com.spasinnya.mentoring.presentation.designsystem.composable.CorePrimaryButton
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVertical
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVerticalLarge
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextBody
import org.jetbrains.compose.resources.vectorResource

@Composable
fun NewPasswordScreen(
    navigateToSuccess: () -> Unit,
) {
    val (viewModel, state) = rememberScreenModel<NewPasswordViewModel, NewPasswordContract.State, NewPasswordContract.Effect>()

    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                imageVector = vectorResource(Res.drawable.ic_logo),
                contentDescription = null
            )
        }
        CoreSpacerVertical(height = 40.dp)
        CoreTextBody(text = "Створіть новий пароль \uD83D\uDD13")
        CoreSpacerVerticalLarge()
        /*PasswordInput(
            password = state.password,
            passwordError = state.passwordError,
            isVisible = state.isPasswordVisible,
            onToggleVisibility = { viewModel.dispatchEvent(LoginContract.Event.PasswordVisibilityToggled(it)) },
            onValueChange = { viewModel.dispatchEvent(LoginContract.Event.PasswordChanged(it)) }
        )*/
        /*CoreOutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = "",
            onValueChange = {  },
            inputDefaults = InputPasswordDefaults()
        )*/
        CoreSpacerVerticalLarge()
        /*CoreOutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = "",
            onValueChange = {  },
            inputDefaults = InputPasswordDefaults()
        )*/
        CoreSpacerVerticalLarge()
        CorePrimaryButton(
            text = "Зберегти",
            iconAfter = Res.drawable.ic_check,
            onClick = { navigateToSuccess.invoke() },
            iconTint = Color.White
        )
    }
}