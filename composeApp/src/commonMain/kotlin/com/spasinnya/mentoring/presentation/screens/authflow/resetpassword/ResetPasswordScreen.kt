package com.spasinnya.mentoring.presentation.screens.authflow.resetpassword

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import books.composeapp.generated.resources.Res
import books.composeapp.generated.resources.ic_repeat
import org.jetbrains.compose.resources.vectorResource

@Composable
fun ResetPasswordScreen(
    navigateToLogin: () -> Unit,
    navigateToOtp: () -> Unit
) {

    val viewModel: ResetPasswordViewModel = viewModel(factory = ResetPasswordViewModel.Factory)
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        Text(
            text = "Скидання паролю \uD83D\uDD13",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.h5.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3C4E73)
            ),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Вкажіть email вашого акаунту",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.subtitle1.copy(
                fontSize = 18.sp,
                color = Color(0xFF828EA0)
            ),
        )
        Spacer(modifier = Modifier.height(40.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            textStyle = MaterialTheme.typography.subtitle1.copy(
                color = Color(0xFF54595F)
            ),
            onValueChange = { email = it },
            placeholder = {
                Text(
                    text = "email@website.com",
                    style = MaterialTheme.typography.subtitle1.copy(
                        color = Color(0xFFB6C3D8)
                    )
                )
            }
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            modifier = Modifier.fillMaxWidth().height(68.dp),
            shape = RoundedCornerShape(40.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF3C4E73)
            ),
            onClick = { navigateToOtp.invoke() },
            content = {
                Text(
                    text = "Скинути пароль",
                    style = MaterialTheme.typography.subtitle1.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Normal
                    )
                )
                Spacer(modifier = Modifier.width(24.dp))
                Icon(
                    imageVector = vectorResource(Res.drawable.ic_repeat),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(onClick = {  }) {
            Text(
                text = "Я згадав пароль",
                style = MaterialTheme.typography.subtitle1.copy(
                    color = Color(0xFF3C4E73),
                    textDecoration = TextDecoration.Underline
                )
            )
        }
    }
}