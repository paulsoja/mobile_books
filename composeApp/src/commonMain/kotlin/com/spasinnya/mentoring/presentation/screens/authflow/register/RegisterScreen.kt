package com.spasinnya.mentoring.presentation.screens.authflow.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import books.composeapp.generated.resources.Res
import books.composeapp.generated.resources.ic_arrow_right
import books.composeapp.generated.resources.ic_google
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
import com.spasinnya.mentoring.presentation.di.viewmodelfactory.createRegisterViewModel

@Composable
fun RegisterScreen(
    navigateToLogin: () -> Unit,
    navigateToOtp: () -> Unit
) {

    val viewModel: RegisterViewModel = viewModel(factory = createRegisterViewModel)
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CoreSpacerVerticalXLarge()

        CoreTextScreenTitle(
            text = "Вітаємо \uD83D\uDC4B"
        )
        CoreSpacerVerticalMedium()

        CoreTextSubtitle(
            text = "Давайте створимо ваш акаунт",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
        )
        CoreSpacerVertical(height = 20.dp)
        CoreTextSubtitle(
            text = "Email",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.titleSmall.copy(
                color = Color(0xFF3C4E73)
            ),
        )
        CoreSpacerVerticalMedium()
        CoreOutlinedTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = "email@website.com",
            textStyle = MaterialTheme.typography.bodySmall.copy(
                color = Color(0xFF54595F)
            )
        )

        CoreSpacerVerticalLarge()

        CoreTextSubtitle(
            text = "Пароль",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.titleSmall.copy(
                color = Color(0xFF3C4E73)
            ),
        )

        CoreSpacerVerticalMedium()

        CoreOutlinedTextField(
            value = password,
            onValueChange = { password = it },
            textStyle = MaterialTheme.typography.bodySmall.copy(
                color = Color(0xFF54595F)
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        CorePrimaryButton(
            backgroundColor = Color(0xFF3C4E73),
            text = "Зареєструватись",
            iconAfter = Res.drawable.ic_arrow_right,
            iconTint = Color.White,
            onClick = { navigateToOtp.invoke() },
        )

        CoreHorizontalDividerWithText("або")

        CoreSpacerVerticalLarge()

        CorePrimaryButton(
            text = "Зареєструватись з Google",
            backgroundColor = Color.White,
            onClick = {  },
            iconBefore = Res.drawable.ic_google,
            iconTint = Color.Unspecified
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            CoreTextBody(
                text = "Вже маєте акаунт?"
            )

            CoreTextButton(text = "Увійти", onClick = navigateToLogin)
        }
    }
}