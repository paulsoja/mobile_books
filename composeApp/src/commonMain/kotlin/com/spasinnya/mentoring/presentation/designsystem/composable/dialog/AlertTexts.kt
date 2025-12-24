package com.spasinnya.mentoring.presentation.designsystem.composable.dialog

import androidx.compose.runtime.Composable
import books.composeapp.generated.resources.Res
import books.composeapp.generated.resources.common_try_again
import books.composeapp.generated.resources.error_check_connection
import books.composeapp.generated.resources.error_no_connection
import com.spasinnya.mentoring.presentation.model.UiErrorType
import org.jetbrains.compose.resources.stringResource

data class AlertTexts(
    val title: String,
    val message: String,
    val confirm: String,
    val dismiss: String,
)

@Composable
fun UiErrorType.toAlertTexts(): AlertTexts =
    when (this) {
        UiErrorType.NoInternet -> AlertTexts(
            title = stringResource(Res.string.error_no_connection),
            message = stringResource(Res.string.error_check_connection),
            confirm = stringResource(Res.string.common_try_again),
            dismiss = stringResource(Res.string.common_try_again),
        )

        UiErrorType.Client -> AlertTexts(
            title = stringResource(Res.string.error_no_connection),
            message = stringResource(Res.string.error_check_connection),
            confirm = stringResource(Res.string.common_try_again),
            dismiss = stringResource(Res.string.common_try_again),
        )

        UiErrorType.Server -> AlertTexts(
            title = stringResource(Res.string.error_no_connection),
            message = stringResource(Res.string.error_check_connection),
            confirm = stringResource(Res.string.common_try_again),
            dismiss = stringResource(Res.string.common_try_again),
        )

        UiErrorType.Unknown,
        UiErrorType.None-> AlertTexts(
            title = stringResource(Res.string.error_no_connection),
            message = stringResource(Res.string.error_check_connection),
            confirm = stringResource(Res.string.common_try_again),
            dismiss = stringResource(Res.string.common_try_again),
        )
    }

