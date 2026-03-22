package com.spasinnya.mentoring.presentation.designsystem.composable.states

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spasinnya.mentoring.presentation.designsystem.composable.CorePrimaryButton
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVertical
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreText
import com.spasinnya.mentoring.presentation.designsystem.composable.dialog.AlertTexts

@Composable
fun ErrorState(
    modifier: Modifier = Modifier,
    texts: AlertTexts,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CoreText(
            text = "\uD83D\uDEA7",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 100.sp
            )
        )
        CoreSpacerVertical(40.dp)
        CoreText(
            text = texts.message,
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Normal,
                lineHeight = 28.sp,
                fontSize = 18.sp,
                color = Color(0xFF828EA0)
            ),
            textAlign = TextAlign.Center
        )
        CoreSpacerVertical(64.dp)
        CorePrimaryButton(
            onClick = onClick,
            text = texts.confirm
        )
    }
}