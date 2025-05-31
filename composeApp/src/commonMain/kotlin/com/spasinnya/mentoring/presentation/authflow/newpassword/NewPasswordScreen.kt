package com.spasinnya.mentoring.presentation.authflow.newpassword

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
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import books.composeapp.generated.resources.Res
import books.composeapp.generated.resources.ic_check
import org.jetbrains.compose.resources.vectorResource

@Composable
fun NewPasswordScreen(
    navigateToSuccess: () -> Unit,
) {

    val viewModel: NewPasswordViewModel = viewModel(factory = NewPasswordViewModel.Factory)

    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Text(text = "Створіть новий пароль \uD83D\uDD13")
        Spacer(modifier = Modifier.height(24.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = "",
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            onValueChange = {  },
            label = { Text("Password") }
        )
        Spacer(modifier = Modifier.height(24.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = "",
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            onValueChange = {  },
            label = { Text("Password") }
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            modifier = Modifier.fillMaxWidth().height(68.dp),
            shape = RoundedCornerShape(40.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF3C4E73)
            ),
            onClick = { navigateToSuccess.invoke() },
            content = {
                Text("Зберегти", color = Color.White)
                Spacer(modifier = Modifier.width(24.dp))
                Icon(
                    imageVector = vectorResource(Res.drawable.ic_check),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        )
    }
}