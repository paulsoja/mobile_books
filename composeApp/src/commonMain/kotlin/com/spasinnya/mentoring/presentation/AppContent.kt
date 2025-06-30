package com.spasinnya.mentoring.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.spasinnya.mentoring.presentation.screens.authflow.AuthFlowContainer
import com.spasinnya.mentoring.presentation.screens.homeflow.HomeFlowContainer
import com.spasinnya.mentoring.presentation.navigation.ScreenContainer

@Composable
fun AppContent() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ScreenContainer.AuthFlow
    ) {
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
            HomeFlowContainer()
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