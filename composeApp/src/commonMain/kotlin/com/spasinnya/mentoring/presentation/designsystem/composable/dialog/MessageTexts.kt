package com.spasinnya.mentoring.presentation.designsystem.composable.dialog

import androidx.compose.runtime.Composable
import books.composeapp.generated.resources.Res
import books.composeapp.generated.resources.auth_logout
import books.composeapp.generated.resources.common_cancel
import books.composeapp.generated.resources.common_ok
import com.spasinnya.mentoring.presentation.model.UiMessageType
import org.jetbrains.compose.resources.stringResource

@Composable
fun UiMessageType.toMessageTexts(): AlertTexts =
    when (this) {
        UiMessageType.Logout -> AlertTexts(
            title = stringResource(Res.string.auth_logout),
            message = "Текст с вопросом",
            confirm = stringResource(Res.string.common_ok),
            dismiss = stringResource(Res.string.common_cancel)
        )
    }
