package com.spasinnya.mentoring.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.spasinnya.mentoring.domain.model.SessionState
import com.spasinnya.mentoring.presentation.di.viewModelFactory
import com.spasinnya.mentoring.presentation.navigation.ScreenContainer
import com.spasinnya.mentoring.presentation.screens.authflow.AuthFlowContainer
import com.spasinnya.mentoring.presentation.screens.homeflow.HomeFlowContainer

@Composable
fun AppContent() {
    val factory = viewModelFactory { graph, handle ->
        SessionViewModel(
            tokenStore = graph.tokenStore,
            handle = handle
        )
    }

    val viewModel: SessionViewModel = viewModel(factory = factory)
    val session by viewModel.state.collectAsState()

    val startDestination = when (session) {
        SessionState.Loading -> ScreenContainer.SplashFlow
        SessionState.Guest -> ScreenContainer.AuthFlow
        SessionState.Authed -> ScreenContainer.HomeFlow
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