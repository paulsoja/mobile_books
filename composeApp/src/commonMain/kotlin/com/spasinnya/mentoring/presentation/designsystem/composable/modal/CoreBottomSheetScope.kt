package com.spasinnya.mentoring.presentation.designsystem.composable.modal

import androidx.compose.runtime.Stable

@Stable
interface CoreBottomSheetScope<A> {
    /** Emit action from sheet content. Core decides whether to close. */
    fun send(action: A)

    /** Close sheet without emitting any action. */
    fun dismiss()
}