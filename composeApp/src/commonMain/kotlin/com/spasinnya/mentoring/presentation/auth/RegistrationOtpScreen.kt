package com.spasinnya.mentoring.presentation.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreButton
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreOutlinedTextField
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreText

@Composable
fun RegistrationOtpScreen(email: String, password: String, vm: AuthViewModel) {
    var code by remember { mutableStateOf("") }

    Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        CoreText(text = "Enter OTP sent to \$email")
        CoreOutlinedTextField(value = code, onValueChange = { code = it }, label = "OTP Code")
        CoreButton(onClick = { vm.verifyOtp(email, password, code) }, text = "Verify OTP")
    }
}