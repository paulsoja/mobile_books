package com.spasinnya.mentoring.presentation.designsystem.composable.states

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spasinnya.mentoring.presentation.designsystem.Grey800
import com.spasinnya.mentoring.presentation.designsystem.Primary900
import com.spasinnya.mentoring.presentation.designsystem.composable.CorePrimaryButton
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVertical
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreText

@Composable
fun EmptyState(
    title: String,
    message: String,
    modifier: Modifier = Modifier,
    emoji: String = "📚",
    actionText: String? = null,
    onActionClick: (() -> Unit)? = null,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CoreText(
            text = emoji,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 64.sp)
        )
        CoreSpacerVertical(40.dp)
        CoreText(
            text = title,
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Bold,
                lineHeight = 28.sp,
                fontSize = 18.sp,
                color = Primary900
            ),
            textAlign = TextAlign.Center
        )
        CoreSpacerVertical(8.dp)
        CoreText(
            text = message,
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Normal,
                lineHeight = 22.sp,
                fontSize = 14.sp,
                color = Grey800
            ),
            textAlign = TextAlign.Center
        )
        if (actionText != null && onActionClick != null) {
            CoreSpacerVertical(40.dp)
            CorePrimaryButton(
                onClick = onActionClick,
                text = actionText
            )
        }
    }
}
