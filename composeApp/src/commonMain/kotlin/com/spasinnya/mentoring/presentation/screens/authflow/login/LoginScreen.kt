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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.spasinnya.mentoring.presentation.designsystem.defaults.InputEmailDefaults
import com.spasinnya.mentoring.presentation.designsystem.defaults.InputPasswordDefaults
import com.spasinnya.mentoring.presentation.di.viewmodelfactory.createLoginViewModel

@Composable
fun LoginScreen(
    navigateToRegister: () -> Unit,
    navigateToResetPassword: () -> Unit,
    navigateToHome: () -> Unit
) {

    val viewModel: LoginViewModel = viewModel(factory = createLoginViewModel)
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CoreSpacerVerticalXLarge()
        CoreTextScreenTitle(
            text = "Раді знову бачити \uD83D\uDC4B",
            modifier = Modifier.fillMaxWidth(),
        )
        CoreSpacerVerticalMedium()
        CoreTextSubtitle(
            text = "Тут вхід в ваш акаунт",
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
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = { email = it },
            inputDefaults = InputEmailDefaults()
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
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = { password = it },
            inputDefaults = InputPasswordDefaults()
        )
        Spacer(modifier = Modifier.height(24.dp))
        CorePrimaryButton(
            text = "Увійти",
            onClick = { navigateToHome.invoke() },
            iconAfter = Res.drawable.ic_arrow_right
        )

        CoreSpacerVerticalMedium()

        CoreTextButton(text = "Не пам'ятаєте пароль?") {
            navigateToResetPassword.invoke()
        }

        CoreHorizontalDividerWithText(text = "або")

        CoreSpacerVerticalLarge()

        CorePrimaryButton(
            text = "Увійти з Google",
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
            CoreTextBody("Ще не маєте акаунта?")
            CoreTextButton(text = "Зареєструватись", onClick = navigateToRegister)
        }
    }
}