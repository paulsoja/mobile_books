package com.spasinnya.mentoring.presentation.screens.authflow.newpassword

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import books.composeapp.generated.resources.Res
import books.composeapp.generated.resources.ic_check
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreOutlinedTextField
import com.spasinnya.mentoring.presentation.designsystem.composable.CorePrimaryButton
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVertical
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVerticalLarge
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextBody
import com.spasinnya.mentoring.presentation.di.viewmodelfactory.createNewPasswordViewModel

@Composable
fun NewPasswordScreen(
    navigateToSuccess: () -> Unit,
) {

    val viewModel: NewPasswordViewModel = viewModel(factory = createNewPasswordViewModel)

    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CoreSpacerVertical(height = 40.dp)
        CoreTextBody(text = "Створіть новий пароль \uD83D\uDD13")
        CoreSpacerVerticalLarge()
        CoreOutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = "",
            singleLine = true,
            onValueChange = {  },
            label = "Password"
        )
        CoreSpacerVerticalLarge()
        CoreOutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = "",
            singleLine = true,
            onValueChange = {  },
            label = "Password"
        )
        CoreSpacerVerticalLarge()
        CorePrimaryButton(
            text = "Зберегти",
            iconAfter = Res.drawable.ic_check,
            onClick = { navigateToSuccess.invoke() },
            iconTint = Color.White
        )
    }
}