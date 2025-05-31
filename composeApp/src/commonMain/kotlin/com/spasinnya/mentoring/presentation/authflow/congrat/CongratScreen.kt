package com.spasinnya.mentoring.presentation.authflow.congrat

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import books.composeapp.generated.resources.Res
import books.composeapp.generated.resources.ic_arrow_right
import org.jetbrains.compose.resources.vectorResource

@Composable
fun CongratScreen(
    navigateTo: () -> Unit
) {
    SuccessState(
        modifier = Modifier.fillMaxSize().padding(horizontal = 48.dp),
        image = {
            Text(
                text = "\uD83C\uDF89",
                style = MaterialTheme.typography.h1.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 100.sp
                ),
            )
        },
        message = {
            Text(
                text = "ВІТАЄМО!",
                style = MaterialTheme.typography.subtitle1.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFF3C4E73)
                ),
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Ваш акаунт успішно створено",
                style = MaterialTheme.typography.subtitle1.copy(
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                    color = Color(0xFF3C4E73)
                ),
            )
        },
        action = {
            Button(
                modifier = Modifier.fillMaxWidth().height(68.dp),
                shape = RoundedCornerShape(40.dp),
                elevation = null,
                border = BorderStroke(width = 1.dp, color = Color(0xFF3C4E73)),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent
                ),
                onClick = navigateTo,
                content = {
                    Text(
                        text = "Відкрити посібник", color = Color(0xFF54595F),
                        style = MaterialTheme.typography.subtitle1.copy(
                            color = Color(0xFF54595F),
                            fontWeight = FontWeight.Normal
                        )
                    )
                    Spacer(modifier = Modifier.width(24.dp))
                    Icon(
                        imageVector = vectorResource(Res.drawable.ic_arrow_right),
                        contentDescription = null,
                        tint = Color(0xFF54595F)
                    )
                }
            )
        }
    )
}

@Composable
fun SuccessState(
    modifier: Modifier,
    image: @Composable () -> Unit,
    message: @Composable ColumnScope.() -> Unit,
    action: @Composable () -> Unit
) = Column(
    modifier = modifier,
    verticalArrangement = Arrangement.Top,
    horizontalAlignment = Alignment.CenterHorizontally
) {
    Spacer(modifier = Modifier.height(100.dp))
    image()
    Spacer(modifier = Modifier.height(40.dp))
    message()
    Spacer(modifier = Modifier.height(40.dp))
    action()
}