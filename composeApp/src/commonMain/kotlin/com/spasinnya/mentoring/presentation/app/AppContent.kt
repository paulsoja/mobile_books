package com.spasinnya.mentoring.presentation.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.spasinnya.mentoring.domain.enums.Language
import com.spasinnya.mentoring.domain.model.AuthSteps
import com.spasinnya.mentoring.presentation.base.LocalAppLocale
import com.spasinnya.mentoring.presentation.base.ProvideAppLocale
import com.spasinnya.mentoring.presentation.base.rememberScreenModel
import com.spasinnya.mentoring.presentation.navigation.ScreenContainer
import com.spasinnya.mentoring.presentation.screens.authflow.AuthFlowContainer
import com.spasinnya.mentoring.presentation.screens.authflow.congrat.CongratScreen
import com.spasinnya.mentoring.presentation.screens.homeflow.HomeFlowContainer
import io.github.aakira.napier.Napier

@Composable
fun AppContent() {
    val (viewModel, state) = setupSessionScreenModel()

    val startDestination = when (state.authStep) {
        AuthSteps.Init -> ScreenContainer.SplashFlow
        AuthSteps.Auth -> ScreenContainer.AuthFlow
        AuthSteps.Congrats -> ScreenContainer.CongratsFlow
        AuthSteps.Home -> ScreenContainer.HomeFlow
    }

    val currentLanguageTag = LocalAppLocale.current

    LaunchedEffect(currentLanguageTag) {
        if (state.language == Language.System) {
            viewModel.dispatchEvent(
                SessionContract.Event.SetLanguage(
                    Language.fromTag(tag = currentLanguageTag.substringBefore("-"))
                )
            )
        }
    }

    Napier.d("AppContent: 1=${state.language}")

    ProvideAppLocale(language = state.language) {
        val navController = rememberNavController()

        key(state.language, startDestination) {
            NavHost(
                navController = navController,
                startDestination = startDestination
            ) {
                composable<ScreenContainer.SplashFlow> { backStackEntry ->

                }
                composable<ScreenContainer.AuthFlow> {
                    AuthFlowContainer(
                        onLoginSuccess = {
                            navController.navigate(ScreenContainer.HomeFlow) {
                                launchSingleTop = true
                                popUpTo(navController.graph.startDestinationId) {
                                    inclusive = true
                                }
                            }
                        },
                        onRegisterSuccess = {
                            navController.navigate(ScreenContainer.CongratsFlow) {
                                launchSingleTop = true
                                popUpTo(navController.graph.startDestinationId) {
                                    inclusive = true
                                }
                            }
                        }
                    )
                }
                composable<ScreenContainer.CongratsFlow> { backStackEntry ->
                    CongratScreen(
                        navigateTo = {
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

@Composable
fun setupSessionScreenModel(): Pair<SessionViewModel, SessionContract.State> =
    rememberScreenModel<SessionViewModel, SessionContract.State, SessionContract.Effect>(
        create = { graph, handle ->
            SessionViewModel(
                checkAuthStepsUseCase = graph.useCases.authStepsUseCase,
                getAppLocaleUseCase = graph.useCases.getAppLocaleUseCase,
                setAppLocaleUseCase = graph.useCases.setAppLocaleUseCase,
                handle = handle
            )
        },
        getState = { it.state },
        getEffect = { it.effect },
        onEffect = { effect -> }
    )