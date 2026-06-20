package com.spasinnya.mentoring.presentation.screens.authflow.register

import androidx.compose.runtime.Composable
import com.spasinnya.mentoring.presentation.base.rememberScreenModel
import com.spasinnya.mentoring.presentation.designsystem.composable.dialog.AppAlertDialog
import com.spasinnya.mentoring.presentation.designsystem.composable.dialog.toAlertTexts
import com.spasinnya.mentoring.presentation.designsystem.composable.loading.LoadingOverlay

@Composable
fun RegisterScreen(
    navigateToLogin: () -> Unit,
    navigateToOtp: (email: String) -> Unit
) {
    val (viewModel, state) = setupRegisterScreenModel(
        navigateToOtp = navigateToOtp
    )

    RegisterContent(
        state = state,
        navigateToLogin = navigateToLogin,
        onEvent = viewModel::dispatchEvent
    )

    LoadingOverlay(visible = state.isLoading)

    AppAlertDialog(
        state = state.dialog,
        mapTexts = { it.toAlertTexts() },
        onDismiss = { viewModel.dispatchEvent(RegisterContract.Event.DismissDialog) },
        onConfirm = { viewModel.dispatchEvent(RegisterContract.Event.DismissDialog) },
    )
}

@Composable
fun setupRegisterScreenModel(
    navigateToOtp: (String) -> Unit
): Pair<RegisterViewModel, RegisterContract.State> =
    rememberScreenModel<RegisterViewModel, RegisterContract.State, RegisterContract.Effect>(
        onEffect = { effect ->
            when (effect) {
                is RegisterContract.Effect.NavigateToOtp -> navigateToOtp.invoke(effect.email)
            }
        }
    )