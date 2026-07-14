package com.spasinnya.mentoring.presentation.designsystem.composable.question

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVerticalSmall
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextBody
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextTitle

@Composable
fun CoreQuestion(
    question: String,
    modifier: Modifier = Modifier,
    description: String? = null,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        CoreTextTitle(text = question)
        description?.takeIf { it.isNotEmpty() }?.let {
            CoreSpacerVerticalSmall()
            CoreTextBody(text = it)
        }
    }
}
