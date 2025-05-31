package com.spasinnya.mentoring.presentation.authflow.components

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OtpInput(
    onFocusChanged: (FocusState) -> Unit = {},
    onFieldChanged: (String) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val focusRequester = remember { FocusRequester() }

    BasicTextField(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .focusRequester(focusRequester)
            .onFocusChanged {
                onFocusChanged.invoke(it)
            },
        value = "",
        onValueChange = { if (it.length <= 4) onFieldChanged(it) },
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
                                text = "",
                                isError = false,
                            )
                        }
                        Spacer(Modifier.width(8.dp))
                    }
                }
            }
        }
    )
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
            .border(width = 1.dp, color = Color(0xFF828EA0), shape = RoundedCornerShape(12.dp))
            .background(color = Color.Transparent)
            .then(
                if (isError) Modifier.border(border = BorderStroke(1.dp, Color(0xFFED1A3D)))
                else Modifier
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically),
            text = char,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.subtitle1.copy(
                color = Color(0xFF54595F),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            ),
        )
    }
}