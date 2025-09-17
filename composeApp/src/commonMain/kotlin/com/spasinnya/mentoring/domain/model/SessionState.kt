package com.spasinnya.mentoring.domain.model

sealed interface SessionState {
    data object Loading : SessionState
    data object Guest : SessionState
    data object Authed : SessionState
}