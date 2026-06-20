package com.spasinnya.mentoring.presentation.designsystem.composable.dialog

import androidx.compose.runtime.Composable
import com.spasinnya.mentoring.generated.resources.Res
import com.spasinnya.mentoring.generated.resources.auth_logout
import com.spasinnya.mentoring.generated.resources.auth_logout_confirmation
import com.spasinnya.mentoring.generated.resources.common_cancel
import com.spasinnya.mentoring.presentation.model.UiMessageType
import org.jetbrains.compose.resources.stringResource

@Composable
fun UiMessageType.toMessageTexts(): AlertTexts =
    when (this) {
        UiMessageType.Logout -> AlertTexts(
            title = "",
            message = stringResource(Res.string.auth_logout_confirmation),
            confirm = stringResource(Res.string.auth_logout),
            cancel = stringResource(Res.string.common_cancel),
        )
    }
