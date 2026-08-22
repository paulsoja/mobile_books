package com.spasinnya.mentoring.presentation.screens.authflow.newpassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.spasinnya.mentoring.generated.resources.Res
import com.spasinnya.mentoring.generated.resources.auth_create_new_password
import com.spasinnya.mentoring.generated.resources.auth_new_password
import com.spasinnya.mentoring.generated.resources.auth_repeat_password
import com.spasinnya.mentoring.generated.resources.common_save
import com.spasinnya.mentoring.generated.resources.ic_check
import com.spasinnya.mentoring.generated.resources.ic_logo
import com.spasinnya.mentoring.presentation.designsystem.composable.CorePrimaryButton
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVertical
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVerticalMedium
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVerticalX2
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextScreenTitle
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextSubtitle
import com.spasinnya.mentoring.presentation.designsystem.composable.inputs.PasswordConfirmationInput
import com.spasinnya.mentoring.presentation.designsystem.composable.inputs.PasswordInput
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun NewPasswordContent(
    state: NewPasswordContract.State,
    onEvent: (NewPasswordContract.Event) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                imageVector = vectorResource(Res.drawable.ic_logo),
                contentDescription = null
            )
        }
        CoreSpacerVertical(height = 100.dp)
        CoreTextScreenTitle(
            text = stringResource(Res.string.auth_create_new_password),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
        )
        CoreSpacerVertical(height = 40.dp)
        CoreTextSubtitle(
            text = stringResource(Res.string.auth_new_password),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
        )
        CoreSpacerVerticalMedium()
        PasswordInput(
            password = state.password,
            passwordError = state.passwordError,
            isVisible = state.isPasswordVisible,
            onToggleVisibility = { onEvent(NewPasswordContract.Event.PasswordVisibilityToggled(it)) },
            onValueChange = { onEvent(NewPasswordContract.Event.PasswordChanged(it)) }
        )
        CoreSpacerVerticalMedium()
        CoreTextSubtitle(
            text = stringResource(Res.string.auth_repeat_password),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
        )
        CoreSpacerVerticalMedium()
        PasswordConfirmationInput(
            confirmation = state.confirmation,
            confirmationError = state.confirmationError,
            isVisible = state.isConfirmationVisible,
            onToggleVisibility = { onEvent(NewPasswordContract.Event.ConfirmationVisibilityToggled(it)) },
            onValueChange = { onEvent(NewPasswordContract.Event.ConfirmationChanged(it)) }
        )
        CoreSpacerVerticalX2()
        CorePrimaryButton(
            text = stringResource(Res.string.common_save),
            iconAfter = Res.drawable.ic_check,
            iconTint = Color.White,
            onClick = { onEvent(NewPasswordContract.Event.SaveClicked) }
        )
    }
}
