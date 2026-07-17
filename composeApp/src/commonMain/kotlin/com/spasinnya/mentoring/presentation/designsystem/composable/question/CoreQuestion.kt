package com.spasinnya.mentoring.presentation.designsystem.composable.question

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVerticalSmall
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextBody
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextTitle

@Composable
fun CoreQuestion(
    question: AnnotatedString,
    modifier: Modifier = Modifier,
    description: AnnotatedString? = null,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        CoreTextTitle(text = question)
        description?.takeIf { it.isNotEmpty() }?.let {
            CoreSpacerVerticalSmall()
            CoreTextBody(text = it)
        }
    }
}
