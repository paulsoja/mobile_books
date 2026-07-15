package com.spasinnya.mentoring.presentation.screens.authflow.otp

import androidx.compose.runtime.Composable
import com.spasinnya.mentoring.presentation.base.rememberScreenModel
import com.spasinnya.mentoring.presentation.designsystem.composable.dialog.AppAlertDialog
import com.spasinnya.mentoring.presentation.designsystem.composable.dialog.toAlertTexts
import com.spasinnya.mentoring.presentation.designsystem.composable.loading.LoadingOverlay

@Composable
fun OtpScreen(
    navigateTo: () -> Unit
) {
    val (viewModel, state) = setupOtpScreenModel(
        navigateTo = navigateTo
    )

    OtpContent(
        state = state,
        onEvent = viewModel::dispatchEvent,
    )

    LoadingOverlay(visible = state.isLoading)

    AppAlertDialog(
        state = state.dialog,
        mapTexts = { it.toAlertTexts() },
        onDismiss = { viewModel.dispatchEvent(OtpContract.Event.DismissDialog) },
        onConfirm = { viewModel.dispatchEvent(OtpContract.Event.DismissDialog) },
    )
}

@Composable
fun setupOtpScreenModel(
    navigateTo: () -> Unit
): Pair<OtpViewModel, OtpContract.State> =
    rememberScreenModel<OtpViewModel, OtpContract.State, OtpContract.Effect>(
        onEffect = { effect ->
            when (effect) {
                OtpContract.Effect.NavigateToCongratScreen -> navigateTo.invoke()
            }
        }
    )