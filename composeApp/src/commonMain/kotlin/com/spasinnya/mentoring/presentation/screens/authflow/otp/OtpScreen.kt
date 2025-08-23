package com.spasinnya.mentoring.presentation.screens.authflow.otp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import books.composeapp.generated.resources.Res
import books.composeapp.generated.resources.ic_arrow_right
import com.spasinnya.mentoring.presentation.designsystem.composable.CorePrimaryButton
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVertical
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextBody
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextButton
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextScreenTitle
import com.spasinnya.mentoring.presentation.di.viewmodelfactory.createOtpViewModel
import com.spasinnya.mentoring.presentation.screens.authflow.components.OtpInput

@Composable
fun OtpScreen(
    navigateTo: () -> Unit
) {

    val viewModel: OtpViewModel = viewModel(factory = createOtpViewModel)

    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CoreSpacerVertical(height = 60.dp)
        CoreTextScreenTitle(
            text = "Підтвердіть ваш email \uD83D\uDCE8"
        )
        Column(
            modifier = Modifier.fillMaxWidth().padding(top = 40.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            CoreTextBody(
                text = "Код було відправлено на"
            )
            CoreTextBody(
                text = "email@website.com",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color(0xFF3C4E73),
                    fontWeight = FontWeight.Normal,
                    textDecoration = TextDecoration.Underline
                )
            )
        }

        CoreSpacerVertical(height = 40.dp)

        OtpInput(
            onFieldChanged = {}
        )
        CoreSpacerVertical(height = 32.dp)
        CorePrimaryButton(
            text = "Підтвердити",
            onClick = navigateTo,
            backgroundColor = Color(0xFF3C4E73),
            iconAfter = Res.drawable.ic_arrow_right,
            iconTint = Color.White
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            CoreTextBody(
                text = "Не отримали код?"
            )
            CoreTextButton(text = "Надіслати знову", onClick = {})
        }
    }
}