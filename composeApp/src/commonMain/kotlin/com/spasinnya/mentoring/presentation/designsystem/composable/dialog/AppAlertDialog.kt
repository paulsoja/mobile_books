package com.spasinnya.mentoring.presentation.designsystem.composable.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.DialogProperties
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextBody
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextButton
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextTitle

@Composable
fun <T> AppAlertDialog(
    state: DialogState<T>,
    mapTexts: @Composable (T) -> AlertTexts,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    val data = (state as? DialogState.Shown<T>)?.data ?: return
    val texts = mapTexts(data)

    AlertDialog(
        containerColor = Color.White,
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false),
        title = { if (texts.title.isNotBlank()) CoreTextTitle(texts.title) },
        text = { CoreTextBody(texts.message) },
        confirmButton = {
            CoreTextButton(
                text = texts.confirm.uppercase(),
                onClick = onConfirm
            )
        },
        dismissButton = {
            texts.cancel?.let {
                CoreTextButton(
                    text = texts.cancel.uppercase(),
                    onClick = onDismiss
                )
            }
        }
    )
}
