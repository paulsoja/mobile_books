package com.spasinnya.mentoring.presentation.screens.authflow.login

import androidx.compose.runtime.Composable
import com.spasinnya.mentoring.presentation.base.rememberScreenModel
import com.spasinnya.mentoring.presentation.designsystem.composable.dialog.AppAlertDialog
import com.spasinnya.mentoring.presentation.designsystem.composable.dialog.toAlertTexts
import com.spasinnya.mentoring.presentation.designsystem.composable.loading.LoadingOverlay

@Composable
fun LoginScreen(
    navigateToRegister: () -> Unit,
    navigateToResetPassword: () -> Unit,
    navigateToHome: () -> Unit
) {
    val (viewModel, state) = setupLoginScreenModel(
        onNavigateToHome = navigateToHome
    )

    LoginContent(
        state = state,
        onEvent = viewModel::dispatchEvent,
        navigateToRegister = navigateToRegister,
        navigateToResetPassword = navigateToResetPassword,
    )

    LoadingOverlay(visible = state.isLoading)

    AppAlertDialog(
        state = state.dialog,
        mapTexts = { it.toAlertTexts() },
        onDismiss = { viewModel.dispatchEvent(LoginContract.Event.DismissDialog) },
        onConfirm = { viewModel.dispatchEvent(LoginContract.Event.DismissDialog) },
    )
}

@Composable
fun setupLoginScreenModel(
    onNavigateToHome: () -> Unit
): Pair<LoginViewModel, LoginContract.State> =
    rememberScreenModel<LoginViewModel, LoginContract.State, LoginContract.Effect>(
        create = { graph, handle ->
            LoginViewModel(
                loginUseCase = graph.useCases.loginUseCase,
                savedState = handle
            )
        },
        getState = { it.state },
        getEffect = { it.effect },
        onEffect = { effect ->
            when (effect) {
                LoginContract.Effect.NavigateToMain -> onNavigateToHome()
            }
        }
    )