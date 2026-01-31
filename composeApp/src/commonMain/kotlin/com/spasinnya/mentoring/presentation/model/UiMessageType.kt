package com.spasinnya.mentoring.presentation.model

sealed class UiMessageType {
    data object Logout : UiMessageType()
}