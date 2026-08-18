package com.spasinnya.mentoring.presentation.screens.authflow.newpassword

import androidx.compose.runtime.Composable
import com.spasinnya.mentoring.presentation.base.rememberScreenModel
import com.spasinnya.mentoring.presentation.designsystem.composable.dialog.AppAlertDialog
import com.spasinnya.mentoring.presentation.designsystem.composable.dialog.toAlertTexts
import com.spasinnya.mentoring.presentation.designsystem.composable.loading.LoadingOverlay

@Composable
fun NewPasswordScreen(
    navigateToLogin: () -> Unit,
) {
    val (viewModel, state) = rememberScreenModel<NewPasswordViewModel, NewPasswordContract.State, NewPasswordContract.Effect>(
        onEffect = { effect ->
            when (effect) {
                NewPasswordContract.Effect.NavigateToLogin -> navigateToLogin.invoke()
            }
        }
    )

    NewPasswordContent(
        state = state,
        onEvent = viewModel::dispatchEvent,
    )

    LoadingOverlay(visible = state.isLoading)

    AppAlertDialog(
        state = state.dialog,
        mapTexts = { it.toAlertTexts() },
        onDismiss = { viewModel.dispatchEvent(NewPasswordContract.Event.DismissDialog) },
        onConfirm = { viewModel.dispatchEvent(NewPasswordContract.Event.DismissDialog) },
    )
}
