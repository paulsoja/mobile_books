package com.spasinnya.mentoring.presentation.screens.homeflow

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.spasinnya.mentoring.presentation.screens.homeflow.home.HomeScreen
import com.spasinnya.mentoring.presentation.screens.homeflow.lessons.LessonsScreen
import com.spasinnya.mentoring.presentation.screens.homeflow.weeks.WeeksScreen
import com.spasinnya.mentoring.presentation.navigation.Screen

@Composable
fun HomeFlowContainer() {

    val navController = rememberNavController()

    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = Screen.HomeFlow.HomeScreen,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        }
    ) {
        composable<Screen.HomeFlow.HomeScreen> {
            HomeScreen(
                navigateToSettings = {  },
                navigateToLessons = {
                    navController.navigate(Screen.HomeFlow.LessonsScreen)
                }
            )
        }
        composable<Screen.HomeFlow.WeeksScreen> {
            WeeksScreen(
                navigateBack = { navController.navigateUp() },
            )
        }
        composable<Screen.HomeFlow.LessonsScreen> {
            LessonsScreen(
                navigateBack = { navController.navigateUp() },
                onActionClicked = {

                },
                navigateToWeek = {
                    navController.navigate(Screen.HomeFlow.WeeksScreen)
                }
            )
        }
    }
}