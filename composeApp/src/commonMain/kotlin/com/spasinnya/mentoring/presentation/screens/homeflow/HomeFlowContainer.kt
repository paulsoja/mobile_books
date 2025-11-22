package com.spasinnya.mentoring.presentation.screens.homeflow

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.spasinnya.mentoring.presentation.navigation.Screen
import com.spasinnya.mentoring.presentation.screens.homeflow.home.HomeScreen
import com.spasinnya.mentoring.presentation.screens.homeflow.weeks.WeeksScreen
import com.spasinnya.mentoring.presentation.screens.homeflow.lessons.LessonsScreen

@Composable
fun HomeFlowContainer(onLogout: () -> Unit) {

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
                navigateToWeeks = { bookId, bookNumber ->
                    navController.navigate(Screen.HomeFlow.WeeksScreen(bookId, bookNumber))
                },
                navigateToProfile = {  },
                navigateToPromoCodes = {  },
                navigateToAuthors = {  },
                navigateToSpasinnyaBooks = {  },
                navigateToSpasinnyaChurch = {  },
                navigateToLogin = onLogout
            )
        }
        composable<Screen.HomeFlow.WeeksScreen> {
            val params = it.toRoute<Screen.HomeFlow.WeeksScreen>()
            WeeksScreen(
                bookNumber = params.bookNumber,
                bookId = params.bookId,
                navigateBack = { navController.navigateUp() },
                onActionClicked = {

                },
                navigateToLessons = { bookId, weekId ->
                    navController.navigate(Screen.HomeFlow.LessonsScreen(bookId, weekId))
                }
            )

        }
        composable<Screen.HomeFlow.LessonsScreen> {
            val params = it.toRoute<Screen.HomeFlow.LessonsScreen>()
            LessonsScreen(
                bookId = params.bookId,
                weekId = params.weekId,
                navigateBack = { navController.navigateUp() },
            )
        }
    }
}