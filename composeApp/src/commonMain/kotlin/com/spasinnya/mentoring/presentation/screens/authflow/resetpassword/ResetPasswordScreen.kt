package com.spasinnya.mentoring.presentation.screens.authflow.resetpassword

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import books.composeapp.generated.resources.Res
import books.composeapp.generated.resources.ic_repeat
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreOutlinedTextField
import com.spasinnya.mentoring.presentation.designsystem.composable.CorePrimaryButton
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVertical
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVerticalMedium
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVerticalX2
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextButton
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextScreenTitle
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextSubtitle
import com.spasinnya.mentoring.presentation.designsystem.defaults.InputEmailDefaults
import com.spasinnya.mentoring.presentation.di.viewmodelfactory.createResetPasswordViewModel

@Composable
fun ResetPasswordScreen(
    navigateToLogin: () -> Unit,
    navigateToOtp: () -> Unit
) {

    val viewModel: ResetPasswordViewModel = viewModel(factory = createResetPasswordViewModel)
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CoreSpacerVertical(height = 100.dp)
        CoreTextScreenTitle(
            text = "Скидання паролю \uD83D\uDD13",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
        )
        CoreSpacerVerticalMedium()
        CoreTextSubtitle(
            text = "Вкажіть email вашого акаунту",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
        )
        CoreSpacerVertical(height = 40.dp)
        CoreOutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = { email = it },
            inputDefaults = InputEmailDefaults()
        )
        CoreSpacerVerticalX2()
        CorePrimaryButton(
            onClick = { navigateToOtp.invoke() },
            text = "Скинути пароль",
            iconAfter = Res.drawable.ic_repeat
        )
        CoreSpacerVerticalMedium()
        CoreTextButton(
            text = "Я згадав пароль",
            onClick = { navigateToLogin.invoke() }
        )
    }
}