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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.spasinnya.mentoring.domain.enums.OtpPurpose
import com.spasinnya.mentoring.presentation.navigation.Screen
import com.spasinnya.mentoring.presentation.screens.authflow.login.LoginScreen
import com.spasinnya.mentoring.presentation.screens.authflow.newpassword.NewPasswordScreen
import com.spasinnya.mentoring.presentation.screens.authflow.otp.OtpScreen
import com.spasinnya.mentoring.presentation.screens.authflow.register.RegisterScreen
import com.spasinnya.mentoring.presentation.screens.authflow.resetpassword.ResetPasswordScreen

@Composable
fun AuthFlowContainer(
    onAuthSuccess: () -> Unit,
) {

    val navController = rememberNavController()
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
                            navController.navigate(
                                Screen.AuthFlow.OtpScreen(email = email, purpose = OtpPurpose.LOGIN.name)
                            )
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
                        navigateToHome = onAuthSuccess
                    )
                }
                composable<Screen.AuthFlow.OtpScreen> {
                    OtpScreen(
                        navigateToHome = onAuthSuccess,
                        navigateToNewPassword = { email, code ->
                            navController.navigate(
                                Screen.AuthFlow.NewPasswordScreen(email = email, code = code)
                            )
                        }
                    )
                }
                composable<Screen.AuthFlow.ResetPasswordScreen> {
                    ResetPasswordScreen(
                        navigateToLogin = {
                            navController.navigateUp()
                        },
                        navigateToOtp = { email ->
                            navController.navigate(
                                Screen.AuthFlow.OtpScreen(email = email, purpose = OtpPurpose.PASSWORD_RESET.name)
                            )
                        }
                    )
                }
                composable<Screen.AuthFlow.NewPasswordScreen> {
                    NewPasswordScreen(
                        navigateToLogin = {
                            navController.navigate(Screen.AuthFlow.LoginScreen) {
                                popUpTo(Screen.AuthFlow.LoginScreen) { inclusive = true }
                            }
                        }
                    )
                }
            }
        }
    )
}