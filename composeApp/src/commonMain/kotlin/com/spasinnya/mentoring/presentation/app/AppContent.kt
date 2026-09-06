package com.spasinnya.mentoring.presentation.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.spasinnya.mentoring.domain.model.AuthSteps
import com.spasinnya.mentoring.presentation.base.ProvideAppLocale
import com.spasinnya.mentoring.presentation.base.rememberScreenModel
import com.spasinnya.mentoring.presentation.designsystem.BooksTheme
import com.spasinnya.mentoring.generated.resources.Res
import com.spasinnya.mentoring.generated.resources.common_try_again
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreCircularProgressIndicator
import com.spasinnya.mentoring.presentation.designsystem.composable.dialog.toAlertTexts
import com.spasinnya.mentoring.presentation.designsystem.composable.states.ErrorState
import com.spasinnya.mentoring.presentation.model.UiErrorType
import com.spasinnya.mentoring.presentation.navigation.ScreenContainer
import com.spasinnya.mentoring.presentation.screens.authflow.AuthFlowContainer
import com.spasinnya.mentoring.presentation.screens.homeflow.HomeFlowContainer
import org.jetbrains.compose.resources.stringResource

@Composable
fun AppContent() {
    val (viewModel, state) = setupSessionScreenModel()

    val startDestination = when (state.authStep) {
        AuthSteps.Init -> ScreenContainer.SplashFlow
        AuthSteps.Auth -> ScreenContainer.AuthFlow
        AuthSteps.Home -> ScreenContainer.HomeFlow
    }

    ProvideAppLocale(language = state.language) {
        BooksTheme(darkTheme = false) {
            val navController = rememberNavController()

            key(state.language, startDestination) {
                NavHost(
                    navController = navController,
                    startDestination = startDestination
                ) {
                    composable<ScreenContainer.SplashFlow> {
                        SplashContent(
                            error = state.error,
                            onRetry = { viewModel.dispatchEvent(SessionContract.Event.Retry) }
                        )
                    }
                    composable<ScreenContainer.AuthFlow> {
                        AuthFlowContainer(
                            onAuthSuccess = {
                                navController.navigate(ScreenContainer.HomeFlow) {
                                    launchSingleTop = true
                                    popUpTo(navController.graph.startDestinationId) {
                                        inclusive = true
                                    }
                                }
                            }
                        )
                    }
                    composable<ScreenContainer.HomeFlow> {
                        HomeFlowContainer {
                            navController.navigate(ScreenContainer.AuthFlow) {
                                popUpTo(0) { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    }
                    composable<ScreenContainer.SettingsFlow> { backStackEntry ->

                    }
                    composable<ScreenContainer.PaymentFlow> {

                    }
                    composable<ScreenContainer.PromoCodesFlow> { backStackEntry ->

                    }
                    composable<ScreenContainer.LessonsFlow> { backStackEntry ->

                    }
                }
            }
        }
    }
}

@Composable
fun setupSessionScreenModel(): Pair<SessionViewModel, SessionContract.State> =
    rememberScreenModel<SessionViewModel, SessionContract.State, SessionContract.Effect>()

@Composable
private fun SplashContent(
    error: UiErrorType,
    onRetry: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize().background(Color(0xFFF5F7FC)),
        contentAlignment = Alignment.Center
    ) {
        if (error == UiErrorType.None) {
            CoreCircularProgressIndicator()
        } else {
            ErrorState(
                modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
                texts = error.toAlertTexts().copy(confirm = stringResource(Res.string.common_try_again)),
                onClick = onRetry
            )
        }
    }
}
