package com.spasinnya.mentoring.domain.model

import androidx.compose.runtime.Composable
import books.composeapp.generated.resources.Res
import books.composeapp.generated.resources.error_check_connection
import books.composeapp.generated.resources.error_no_connection
import books.composeapp.generated.resources.error_try_again_later
import books.composeapp.generated.resources.error_unexpected
import org.jetbrains.compose.resources.stringResource

enum class UiErrorType(
    val title: @Composable () -> String,
    val message: @Composable () -> String,
) {
    NoConnection(
        title = { stringResource(Res.string.error_no_connection) },
        message = { stringResource(Res.string.error_check_connection) },
    ),
    Unexpected(
        title = { stringResource(Res.string.error_unexpected) },
        message = { stringResource(Res.string.error_try_again_later) },
    ),
}