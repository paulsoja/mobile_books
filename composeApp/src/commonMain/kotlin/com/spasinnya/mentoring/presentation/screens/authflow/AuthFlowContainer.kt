package com.spasinnya.mentoring.presentation.screens.authflow

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.spasinnya.mentoring.presentation.navigation.Screen
import com.spasinnya.mentoring.presentation.navigation.ScreenContainer
import com.spasinnya.mentoring.presentation.screens.authflow.login.LoginScreen
import com.spasinnya.mentoring.presentation.screens.authflow.newpassword.NewPasswordScreen
import com.spasinnya.mentoring.presentation.screens.authflow.otp.OtpScreen
import com.spasinnya.mentoring.presentation.screens.authflow.register.RegisterScreen
import com.spasinnya.mentoring.presentation.screens.authflow.resetpassword.ResetPasswordScreen
import kotlinx.coroutines.flow.filter

@Composable
fun AuthFlowContainer(
    onLoginSuccess: () -> Unit,
    onRegisterSuccess: () -> Unit,
) {

    val navController = rememberNavController()
    LaunchedEffect(navController) {
        snapshotFlow { navController.currentBackStackEntry?.destination?.route }
            .filter { it == ScreenContainer.HomeFlow.toString() }
            .collect { onLoginSuccess() }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFFF5F7FC),
        content = { innerPadding ->
            NavHost(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState()).systemBarsPadding().imePadding(),
                navController = navController,
                startDestination = Screen.AuthFlow.LoginScreen,
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
                composable<Screen.AuthFlow.RegisterScreen> {
                    RegisterScreen(
                        navigateToLogin = {
                            navController.navigate(Screen.AuthFlow.LoginScreen)
                        },
                        navigateToOtp = { email ->
                            navController.navigate(Screen.AuthFlow.OtpScreen(email))
                        }
                    )
                }
                composable<Screen.AuthFlow.LoginScreen> {
                    LoginScreen(
                        navigateToRegister = {
                            navController.navigate(Screen.AuthFlow.RegisterScreen)
                        },
                        navigateToResetPassword = {
                            navController.navigate(Screen.AuthFlow.ResetPasswordScreen)
                        },
                        navigateToHome = {
                            onLoginSuccess()
                        }
                    )
                }
                composable<Screen.AuthFlow.OtpScreen> { backStackEntry ->
                    OtpScreen(
                        navigateTo = {
                            onRegisterSuccess()
                        }
                    )
                }
                composable<Screen.AuthFlow.ResetPasswordScreen> {
                    ResetPasswordScreen(
                        navigateToLogin = {
                            navController.navigateUp()
                        },
                        navigateToOtp = { email ->
                            navController.navigate(Screen.AuthFlow.OtpScreen(email))
                        }
                    )
                }
                composable<Screen.AuthFlow.NewPasswordScreen> {
                    NewPasswordScreen(
                        navigateToSuccess = onLoginSuccess
                    )
                }
            }
        }
    )
}