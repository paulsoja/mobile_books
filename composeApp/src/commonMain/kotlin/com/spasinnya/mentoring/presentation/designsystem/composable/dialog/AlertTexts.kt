package com.spasinnya.mentoring.presentation.designsystem.composable.dialog

import androidx.compose.runtime.Composable
import com.spasinnya.mentoring.generated.resources.Res
import com.spasinnya.mentoring.generated.resources.common_ok
import com.spasinnya.mentoring.generated.resources.error_check_connection
import com.spasinnya.mentoring.generated.resources.error_no_connection
import com.spasinnya.mentoring.generated.resources.error_try_again_later
import com.spasinnya.mentoring.generated.resources.error_unexpected
import com.spasinnya.mentoring.presentation.model.UiErrorType
import org.jetbrains.compose.resources.stringResource

data class AlertTexts(
    val title: String,
    val message: String,
    val confirm: String,
    val cancel: String? = null
)

@Composable
fun UiErrorType.toAlertTexts(): AlertTexts =
    when (this) {
        UiErrorType.NoInternet -> AlertTexts(
            title = stringResource(Res.string.error_no_connection),
            message = stringResource(Res.string.error_check_connection),
            confirm = stringResource(Res.string.common_ok),
        )

        UiErrorType.Client -> AlertTexts(
            title = stringResource(Res.string.error_no_connection),
            message = stringResource(Res.string.error_check_connection),
            confirm = stringResource(Res.string.common_ok),
        )

        UiErrorType.Server -> AlertTexts(
            title = stringResource(Res.string.error_unexpected),
            message = stringResource(Res.string.error_try_again_later),
            confirm = stringResource(Res.string.common_ok),
        )

        UiErrorType.Unknown -> AlertTexts(
            title = stringResource(Res.string.error_no_connection),
            message = stringResource(Res.string.error_check_connection),
            confirm = stringResource(Res.string.common_ok),
        )
        UiErrorType.None-> AlertTexts(
            title = "",
            message = "",
            confirm = "",
        )
    }

