package com.spasinnya.mentoring.presentation.screens.authflow.otp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import books.composeapp.generated.resources.Res
import books.composeapp.generated.resources.ic_arrow_right
import com.spasinnya.mentoring.presentation.di.viewmodelfactory.createOtpViewModel
import com.spasinnya.mentoring.presentation.screens.authflow.components.OtpInput
import org.jetbrains.compose.resources.vectorResource

@Composable
fun OtpScreen(
    navigateTo: () -> Unit
) {

    val viewModel: OtpViewModel = viewModel(factory = createOtpViewModel)

    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        Text(
            text = "Підтвердіть ваш email \uD83D\uDCE8",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.h5.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3C4E73)
            ),
        )
        Column(
            modifier = Modifier.fillMaxWidth().padding(top = 40.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Код було відправлено на",
                style = MaterialTheme.typography.subtitle1.copy(
                    color = Color(0xFF828EA0),
                    fontWeight = FontWeight.Normal
                )
            )
            Text(
                text = "email@website.com",
                style = MaterialTheme.typography.subtitle1.copy(
                    color = Color(0xFF3C4E73),
                    fontWeight = FontWeight.Normal,
                    textDecoration = TextDecoration.Underline
                )
            )
        }

        Spacer(modifier = Modifier.height(40.dp))
        OtpInput(
            onFieldChanged = {}
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            modifier = Modifier.fillMaxWidth().height(68.dp),
            shape = RoundedCornerShape(40.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF3C4E73)
            ),
            onClick = navigateTo,
            content = {
                Text(
                    text = "Підтвердити",
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
            modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Не отримали код?",
                style = MaterialTheme.typography.subtitle2.copy(
                    color = Color(0xFF828EA0),
                    fontWeight = FontWeight.Normal
                )
            )
            TextButton(onClick = {}) {
                Text(
                    text = "Надіслати знову",
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