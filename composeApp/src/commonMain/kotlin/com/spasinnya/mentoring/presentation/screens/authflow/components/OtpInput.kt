package com.spasinnya.mentoring.presentation.screens.authflow.components

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVertical
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreText
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextSubtitle

@Composable
fun OtpInput(
    value: String,
    errorMessage: String = "",
    onFocusChanged: (FocusState) -> Unit = {},
    onFieldChanged: (String) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val focusRequester = remember { FocusRequester() }
    var valueRaw by remember(value) { mutableStateOf(value) }

    val keyboardController = LocalSoftwareKeyboardController.current

    BasicTextField(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .focusRequester(focusRequester)
            .onFocusChanged(onFocusChanged),
        value = valueRaw,
        onValueChange = {
            if (it.length <= 4) {
                valueRaw = it
                onFieldChanged(it)
            }
            if (it.length == 4) {
                keyboardController?.hide()
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
                    repeat(4) { index ->
                        Row(
                            modifier = Modifier
                                .height(72.dp)
                                .weight(1f),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            CharView(
                                index = index,
                                text = valueRaw,
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
            style = MaterialTheme.typography.titleMedium.copy( // TODO refactor this to proper style system
                color = Color(0xFF54595F),
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            ),
        )
    }
}