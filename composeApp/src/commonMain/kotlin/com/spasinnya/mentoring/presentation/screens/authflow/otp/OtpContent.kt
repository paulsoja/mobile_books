package com.spasinnya.mentoring.presentation.screens.authflow.otp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.spasinnya.mentoring.generated.resources.Res
import com.spasinnya.mentoring.generated.resources.auth_otp_code_sent
import com.spasinnya.mentoring.generated.resources.auth_otp_confirm_your_email
import com.spasinnya.mentoring.generated.resources.auth_otp_no_code
import com.spasinnya.mentoring.generated.resources.auth_otp_send_again
import com.spasinnya.mentoring.generated.resources.common_confirm
import com.spasinnya.mentoring.generated.resources.ic_arrow_right
import com.spasinnya.mentoring.generated.resources.ic_logo
import com.spasinnya.mentoring.presentation.designsystem.composable.CorePrimaryButton
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVertical
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextBody
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextButton
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextScreenTitle
import com.spasinnya.mentoring.presentation.designsystem.composable.inputs.OtpInput
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun OtpContent(
    state: OtpContract.State,
    onEvent: (OtpContract.Event) -> Unit,
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
        CoreSpacerVertical(height = 60.dp)
        CoreTextScreenTitle(
            text = stringResource(Res.string.auth_otp_confirm_your_email)
        )
        Column(
            modifier = Modifier.fillMaxWidth().padding(top = 40.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            CoreTextBody(text = stringResource(Res.string.auth_otp_code_sent))
            CoreTextBody(
                text = state.email.value,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color(0xFF3C4E73),
                    fontWeight = FontWeight.Normal,
                    textDecoration = TextDecoration.Underline
                )
            )
        }

        CoreSpacerVertical(height = 40.dp)

        OtpInput(
            otp = state.otp,
            error = state.otpError,
            onOtpChange = { newOtp ->
                onEvent(OtpContract.Event.OtpChanged(newOtp))
            }
        )
        CoreSpacerVertical(height = 32.dp)
        CorePrimaryButton(
            text = stringResource(Res.string.common_confirm),
            backgroundColor = Color(0xFF3C4E73),
            iconAfter = Res.drawable.ic_arrow_right,
            iconTint = Color.White,
            onClick = {
                onEvent(
                    OtpContract.Event.ConfirmClicked(
                        email = state.email,
                        otp = state.otp
                    )
                )
            },
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            CoreTextBody(text = stringResource(Res.string.auth_otp_no_code))
            CoreTextButton(
                text = stringResource(Res.string.auth_otp_send_again),
                onClick = { onEvent(OtpContract.Event.RequestOtp) }
            )
        }
    }
}