package com.spasinnya.mentoring.presentation.screens.authflow.congrat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import books.composeapp.generated.resources.Res
import books.composeapp.generated.resources.ic_arrow_right
import com.spasinnya.mentoring.presentation.designsystem.BooksTheme
import com.spasinnya.mentoring.presentation.designsystem.composable.CorePrimaryButton
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreText
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CongratScreen(
    navigateTo: () -> Unit
) {
    SuccessState(
        modifier = Modifier.fillMaxSize().padding(horizontal = 48.dp),
        image = {
            CoreText(
                text = "\uD83C\uDF89",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 100.sp
                ),
            )
        },
        message = {
            CoreText(
                text = "ВІТАЄМО!",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFF3C4E73)
                ),
            )
            Spacer(modifier = Modifier.height(4.dp))
            CoreText(
                text = "Ваш акаунт успішно створено",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                    color = Color(0xFF3C4E73)
                ),
            )
        },
        action = {
            CorePrimaryButton(
                text = "Відкрити посібник",
                onClick = navigateTo,
                iconAfter = Res.drawable.ic_arrow_right
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

@Preview
@Composable
fun CongratScreenPreview() {
    BooksTheme {
        CongratScreen(navigateTo = {})
    }
}