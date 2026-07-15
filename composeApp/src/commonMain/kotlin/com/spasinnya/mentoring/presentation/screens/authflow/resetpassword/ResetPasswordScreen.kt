package com.spasinnya.mentoring.presentation.screens.authflow.resetpassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.spasinnya.mentoring.generated.resources.Res
import com.spasinnya.mentoring.generated.resources.auth_recall_password
import com.spasinnya.mentoring.generated.resources.auth_reset_password
import com.spasinnya.mentoring.generated.resources.auth_reset_password_process
import com.spasinnya.mentoring.generated.resources.auth_reset_password_type_email
import com.spasinnya.mentoring.generated.resources.ic_logo
import com.spasinnya.mentoring.generated.resources.ic_repeat
import com.spasinnya.mentoring.presentation.base.rememberScreenModel
import com.spasinnya.mentoring.presentation.designsystem.composable.CorePrimaryButton
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVertical
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVerticalMedium
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVerticalX2
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextButton
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextScreenTitle
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextSubtitle
import com.spasinnya.mentoring.presentation.designsystem.composable.inputs.EmailInput
import com.spasinnya.mentoring.presentation.designsystem.defaults.InputEmailDefaults
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun ResetPasswordScreen(
    navigateToLogin: () -> Unit,
    navigateToOtp: (email: String) -> Unit
) {
    val (viewModel, state) = rememberScreenModel<ResetPasswordViewModel, ResetPasswordContract.State, ResetPasswordContract.Effect>(
        onEffect = { effect ->
            when (effect) {
                is ResetPasswordContract.Effect.NavigateToOtp -> navigateToOtp.invoke(effect.email)
            }
        }
    )

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
        CoreSpacerVertical(height = 100.dp)
        CoreTextScreenTitle(
            text = stringResource(Res.string.auth_reset_password_process),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
        )
        CoreSpacerVerticalMedium()
        CoreTextSubtitle(
            text = stringResource(Res.string.auth_reset_password_type_email),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
        )
        CoreSpacerVertical(height = 40.dp)
        EmailInput(
            modifier = Modifier.fillMaxWidth(),
            email = state.email,
            emailError = state.emailError,
            defaults = InputEmailDefaults(),
            onValueChange = { viewModel.dispatchEvent(ResetPasswordContract.Event.EmailChanged(it)) }
        )
        CoreSpacerVerticalX2()
        CorePrimaryButton(
            onClick = { viewModel.dispatchEvent(ResetPasswordContract.Event.ValidateEmail) },
            text = stringResource(Res.string.auth_reset_password),
            iconAfter = Res.drawable.ic_repeat
        )
        CoreSpacerVerticalMedium()
        CoreTextButton(
            text = stringResource(Res.string.auth_recall_password),
            onClick = { navigateToLogin.invoke() }
        )
    }
}