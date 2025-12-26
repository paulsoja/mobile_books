package com.spasinnya.mentoring.presentation.screens.authflow

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import books.composeapp.generated.resources.Res
import books.composeapp.generated.resources.ic_logo
import com.spasinnya.mentoring.presentation.navigation.Screen
import com.spasinnya.mentoring.presentation.navigation.ScreenContainer
import com.spasinnya.mentoring.presentation.screens.authflow.login.LoginScreen
import com.spasinnya.mentoring.presentation.screens.authflow.newpassword.NewPasswordScreen
import com.spasinnya.mentoring.presentation.screens.authflow.otp.OtpScreen
import com.spasinnya.mentoring.presentation.screens.authflow.register.RegisterScreen
import com.spasinnya.mentoring.presentation.screens.authflow.resetpassword.ResetPasswordScreen
import kotlinx.coroutines.flow.filter
import org.jetbrains.compose.resources.vectorResource

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
        modifier = Modifier.fillMaxSize().systemBarsPadding().navigationBarsPadding(),
        containerColor = Color(0xFFF5F7FC),
        topBar = {
            Box(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                contentAlignment = Alignment.Center
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
                        navigateToOtp = {
                            navController.navigate(Screen.AuthFlow.OtpScreen)
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