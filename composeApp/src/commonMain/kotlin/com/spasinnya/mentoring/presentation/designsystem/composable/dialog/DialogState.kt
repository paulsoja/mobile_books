package com.spasinnya.mentoring.presentation.designsystem.composable.dialog

sealed class DialogState<out T> {
    data object Hidden : DialogState<Nothing>()
    data class Shown<T>(val data: T) : DialogState<T>()
}