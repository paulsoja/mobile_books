package com.spasinnya.mentoring.presentation.designsystem.composable.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

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
        onDismissRequest = onDismiss,
        title = { Text(texts.title) },
        text = { Text(texts.message) },
        confirmButton = {
            TextButton(onClick = onConfirm) { Text(texts.confirm) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text(texts.dismiss) }
        }
    )
}
