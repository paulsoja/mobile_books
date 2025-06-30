package com.spasinnya.mentoring.presentation.screens.authflow.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
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
import books.composeapp.generated.resources.ic_arrow_right
import books.composeapp.generated.resources.ic_google
import org.jetbrains.compose.resources.vectorResource

@Composable
fun RegisterScreen(
    navigateToLogin: () -> Unit,
    navigateToOtp: () -> Unit
) {

    val viewModel: RegisterViewModel = viewModel(factory = RegisterViewModel.Factory)
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(36.dp))
        Text(
            text = "Вітаємо \uD83D\uDC4B",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.h5.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3C4E73)
            ),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Давайте створимо ваш акаунт",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.subtitle1.copy(
                fontSize = 18.sp,
                color = Color(0xFF828EA0)
            ),
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Email",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.subtitle1.copy(
                color = Color(0xFF3C4E73)
            ),
        )
        Spacer(modifier = Modifier.height(8.dp))
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
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Пароль",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.subtitle1.copy(
                color = Color(0xFF3C4E73)
            ),
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            textStyle = MaterialTheme.typography.subtitle1.copy(
                color = Color(0xFF54595F)
            ),
            onValueChange = { password = it },
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            modifier = Modifier.fillMaxWidth().height(68.dp),
            shape = RoundedCornerShape(40.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF3C4E73)
            ),
            onClick = { navigateToOtp.invoke() },
            content = {
                Text(
                    text = "Зареєструватись",
                    style = MaterialTheme.typography.subtitle1.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Normal
                    )
                )
                Spacer(modifier = Modifier.width(24.dp))
                Icon(
                    imageVector = vectorResource(Res.drawable.ic_arrow_right),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        )
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Divider(modifier = Modifier.weight(1f), thickness = 1.dp, color = Color(0xFFA6B6CE))
            Text(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(horizontal = 20.dp),
                text = "або",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.subtitle1.copy(
                    color = Color(0xFFA6B6CE)
                )
            )
            Divider(modifier = Modifier.weight(1f), thickness = 1.dp, color = Color(0xFFA6B6CE))
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            modifier = Modifier.fillMaxWidth().height(68.dp),
            shape = RoundedCornerShape(40.dp),
            elevation = null,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.White
            ),
            onClick = {  },
            content = {
                Icon(
                    imageVector = vectorResource(Res.drawable.ic_google),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.width(24.dp))
                Text(
                    text = "Зареєструватись з Google",
                    style = MaterialTheme.typography.subtitle1.copy(
                        color = Color(0xFF54595F),
                        fontWeight = FontWeight.Normal
                    )
                )
            }
        )
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Вже маєте акаунт?",
                style = MaterialTheme.typography.subtitle2.copy(
                    color = Color(0xFF828EA0),
                    fontWeight = FontWeight.Normal
                )
            )
            TextButton(onClick = { navigateToLogin.invoke() },) {
                Text(
                    text = "Увійти",
                    style = MaterialTheme.typography.subtitle2.copy(
                        color = Color(0xFF3C4E73),
                        fontWeight = FontWeight.Normal,
                        textDecoration = TextDecoration.Underline
                    )
                )
            }
        }
    }
}