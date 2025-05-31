package com.spasinnya.mentoring.presentation.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RegistrationOtpScreen(email: String, password: String, vm: AuthViewModel) {
    var code by remember { mutableStateOf("") }

    Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Enter OTP sent to \$email")
        OutlinedTextField(value = code, onValueChange = { code = it }, label = { Text("OTP Code") })
        Button(onClick = { vm.verifyOtp(email, password, code) }) { Text("Verify OTP") }
    }
}