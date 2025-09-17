package com.spasinnya.mentoring.presentation.screens.authflow.otp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import books.composeapp.generated.resources.Res
import books.composeapp.generated.resources.auth_otp_code_sent
import books.composeapp.generated.resources.auth_otp_confirm_your_email
import books.composeapp.generated.resources.auth_otp_no_code
import books.composeapp.generated.resources.auth_otp_send_again
import books.composeapp.generated.resources.common_confirm
import books.composeapp.generated.resources.ic_arrow_right
import com.spasinnya.mentoring.presentation.base.CollectEffects
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreCircularProgressIndicator
import com.spasinnya.mentoring.presentation.designsystem.composable.CorePrimaryButton
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVertical
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextBody
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextButton
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextScreenTitle
import com.spasinnya.mentoring.presentation.di.viewModelFactory
import com.spasinnya.mentoring.presentation.screens.authflow.components.OtpInput
import com.spasinnya.mentoring.presentation.screens.authflow.register.ErrorState
import org.jetbrains.compose.resources.stringResource

@Composable
fun OtpScreen(
    navigateTo: () -> Unit
) {

    val factory = remember {
        viewModelFactory { graph, handle ->
            OtpViewModel(
                otpCodeUseCase = graph.useCases.otpUseCase,
                savedStateHandle = handle
            )
        }
    }
    val viewModel: OtpViewModel = viewModel(factory = factory)
    val state by viewModel.state.collectAsStateWithLifecycle()

    viewModel.effect.CollectEffects { effect ->
        when (effect) {
            is OtpContract.Effect.NavigateToCongratScreen -> navigateTo()
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
                OtpContract.Event.ValidateOtpCredentials(
                    email = state.email.value,
                    code = state.code.value
                )
            )
        },
        onDismiss = {
            viewModel.dispatchEvent(OtpContract.Event.HandleError(OtpContract.ErrorType.NoError))
        }
    )

    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CoreSpacerVertical(height = 60.dp)
        CoreTextScreenTitle(
            text = stringResource(Res.string.auth_otp_confirm_your_email)
        )
        Column(
            modifier = Modifier.fillMaxWidth().padding(top = 40.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            CoreTextBody(text = stringResource(Res.string.auth_otp_code_sent))
            CoreTextBody(
                text = state.email.value,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color(0xFF3C4E73),
                    fontWeight = FontWeight.Normal,
                    textDecoration = TextDecoration.Underline
                )
            )
        }

        CoreSpacerVertical(height = 40.dp)

        OtpInput(
            value = state.code.value,
            errorMessage = state.otpError.message(),
            onFieldChanged = {
                viewModel.dispatchEvent(OtpContract.Event.CodeChanged(it))
            }
        )
        CoreSpacerVertical(height = 32.dp)
        CorePrimaryButton(
            text = stringResource(Res.string.common_confirm),
            backgroundColor = Color(0xFF3C4E73),
            iconAfter = Res.drawable.ic_arrow_right,
            iconTint = Color.White,
            onClick = {
                viewModel.dispatchEvent(
                    OtpContract.Event.ValidateOtpCredentials(
                        state.email.value,
                        state.code.value
                    )
                )
            },
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            CoreTextBody(text = stringResource(Res.string.auth_otp_no_code))
            CoreTextButton(
                text = stringResource(Res.string.auth_otp_send_again),
                onClick = {}
            )
        }
    }
}