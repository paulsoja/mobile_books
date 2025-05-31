package com.spasinnya.mentoring.presentation.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

val LocalAuthViewModel = staticCompositionLocalOf<AuthViewModel> {
    error("AuthViewModel not provided")
}

@Composable
fun AuthFlow() {
    val vm = LocalAuthViewModel.current
    val state = vm.state.collectAsState()

    Box(Modifier.fillMaxSize().statusBarsPadding().padding(16.dp)) {
        when (val state = state.value) {
            is AuthState.Idle -> Unit
            is AuthState.Loading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
            is AuthState.LoginSuccess -> Text("Logged in: \${(state as AuthState.LoginSuccess).token.token}")
            is AuthState.RegisterStep -> RegistrationOtpScreen(
                email = state.email,
                password = state.password,
                vm = vm
            )
            is AuthState.OtpSuccess -> Text(state.message)
            is AuthState.Error -> Text("Error: ${state.message}")
        }
    }
}