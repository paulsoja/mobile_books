package com.spasinnya.mentoring.presentation.authflow

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import books.composeapp.generated.resources.Res
import books.composeapp.generated.resources.ic_logo
import com.spasinnya.mentoring.presentation.authflow.congrat.CongratScreen
import com.spasinnya.mentoring.presentation.authflow.login.LoginScreen
import com.spasinnya.mentoring.presentation.authflow.newpassword.NewPasswordScreen
import com.spasinnya.mentoring.presentation.authflow.otp.OtpScreen
import com.spasinnya.mentoring.presentation.authflow.register.RegisterScreen
import com.spasinnya.mentoring.presentation.authflow.resetpassword.ResetPasswordScreen
import com.spasinnya.mentoring.presentation.navigation.Screen
import com.spasinnya.mentoring.presentation.navigation.ScreenContainer
import kotlinx.coroutines.flow.filter
import org.jetbrains.compose.resources.vectorResource

@Composable
fun AuthFlowContainer(onAuthSuccess: () -> Unit) {

    val navController = rememberNavController()
    LaunchedEffect(navController) {
        snapshotFlow { navController.currentBackStackEntry?.destination?.route }
            .filter { it == ScreenContainer.HomeFlow.toString() }
            .collect { onAuthSuccess() }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize().systemBarsPadding().navigationBarsPadding(),
        backgroundColor = Color(0xFFF5F7FC),
        topBar = {
            Box(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Image(
                    imageVector = vectorResource(Res.drawable.ic_logo),
                    contentDescription = null
                )
            }
        },
        content = { innerPadding ->
            NavHost(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
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
                        navigateToOtp = {
                            navController.navigate(Screen.AuthFlow.OtpScreen)
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
                            onAuthSuccess()
                        }
                    )
                }
                composable<Screen.AuthFlow.OtpScreen> {
                    OtpScreen(
                        navigateTo = {
                            navController.navigate(Screen.AuthFlow.CongratScreen)
                        }
                    )
                }
                composable<Screen.AuthFlow.ResetPasswordScreen> {
                    ResetPasswordScreen(
                        navigateToLogin = {
                            navController.navigateUp()
                        },
                        navigateToOtp = {
                            navController.navigate(Screen.AuthFlow.OtpScreen)
                        }
                    )
                }
                composable<Screen.AuthFlow.NewPasswordScreen> {
                    NewPasswordScreen(
                        navigateToSuccess = onAuthSuccess
                    )
                }
                composable<Screen.AuthFlow.CongratScreen> {
                    CongratScreen(
                        navigateTo = onAuthSuccess
                    )
                }
            }
        }
    )
}