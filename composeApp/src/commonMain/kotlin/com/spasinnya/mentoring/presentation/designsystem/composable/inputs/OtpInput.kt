package com.spasinnya.mentoring.presentation.designsystem.composable.inputs

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spasinnya.mentoring.domain.model.OtpCode
import com.spasinnya.mentoring.generated.resources.Res
import com.spasinnya.mentoring.generated.resources.error_otp_invalid
import com.spasinnya.mentoring.generated.resources.error_otp_not_filled
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVertical
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreText
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextSubtitle
import org.jetbrains.compose.resources.stringResource

private const val OTP_LENGTH = 4

@Composable
fun OtpInput(
    otp: OtpCode,
    error: OtpCode.Error = OtpCode.Error.NoError,
    onOtpChange: (OtpCode) -> Unit,
    onFocusChanged: (FocusState) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val errorMessage = error.toMessage()

    BasicTextField(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp)
            .onFocusChanged(onFocusChanged),
        value = otp.value,
        onValueChange = { newValue ->
            if (newValue.length <= OTP_LENGTH) {
                onOtpChange(OtpCode.raw(newValue))

                if (newValue.length == OTP_LENGTH) {
                    keyboardController?.hide()
                }
            }
        },
        interactionSource = interactionSource,
        enabled = true,
        readOnly = false,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
        ),
        singleLine = true,
        decorationBox = {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(OTP_LENGTH) { index ->
                        Row(
                            modifier = Modifier
                                .height(72.dp)
                                .weight(1f),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            CharView(
                                index = index,
                                text = otp.value,
                                isError = errorMessage.isNotEmpty(),
                            )
                        }
                        Spacer(Modifier.width(8.dp))
                    }
                }
            }
        }
    )

    CoreSpacerVertical(12.dp)

    AnimatedVisibility(errorMessage.isNotEmpty()) {
        CoreText(
            modifier = Modifier.fillMaxWidth(),
            text = errorMessage,
            style = MaterialTheme.typography.bodySmall.copy(
                color = Color(0xFFED1A3D),
                fontWeight = FontWeight.Normal,
            ),
        )
    }
}

@Composable
private fun CharView(
    index: Int,
    text: String,
    isError: Boolean = false,
) {
    val char = when {
        index == text.length -> ""
        index > text.length -> ""
        else -> text[index].toString()
    }

    Row(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(12.dp))
            .border(
                width = 1.dp,
                color = if (isError) Color(0xFFED1A3D) else Color(0xFF828EA0),
                shape = RoundedCornerShape(12.dp)
            )
            .background(color = Color.Transparent),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        CoreTextSubtitle(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically),
            text = char,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium.copy(
                color = Color(0xFF54595F),
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            ),
        )
    }
}

@Composable
fun OtpCode.Error.toMessage(): String =
    when (this) {
        OtpCode.Error.NoError -> ""
        OtpCode.Error.InvalidFormat -> stringResource(Res.string.error_otp_invalid)
        OtpCode.Error.NotFilled -> stringResource(Res.string.error_otp_not_filled)
    }