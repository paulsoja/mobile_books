package com.spasinnya.mentoring.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.spasinnya.mentoring.domain.model.AuthSteps
import com.spasinnya.mentoring.presentation.di.viewModelFactory
import com.spasinnya.mentoring.presentation.navigation.ScreenContainer
import com.spasinnya.mentoring.presentation.screens.authflow.AuthFlowContainer
import com.spasinnya.mentoring.presentation.screens.authflow.congrat.CongratScreen
import com.spasinnya.mentoring.presentation.screens.homeflow.HomeFlowContainer

@Composable
fun AppContent() {
    val factory = viewModelFactory { graph, handle ->
        SessionViewModel(
            checkAuthStepsUseCase = graph.useCases.authStepsUseCase,
            handle = handle
        )
    }

    val viewModel: SessionViewModel = viewModel(factory = factory)
    val currentAuthStep by viewModel.state.collectAsStateWithLifecycle()

    val startDestination = when (currentAuthStep) {
        AuthSteps.Init -> ScreenContainer.SplashFlow
        AuthSteps.Auth -> ScreenContainer.AuthFlow
        AuthSteps.Congrats -> ScreenContainer.CongratsFlow
        AuthSteps.Home -> ScreenContainer.HomeFlow
    }

    val navController = rememberNavController()
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