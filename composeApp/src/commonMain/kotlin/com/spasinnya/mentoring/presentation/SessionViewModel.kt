package com.spasinnya.mentoring.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spasinnya.mentoring.data.storage.datastore.TokenStore
import com.spasinnya.mentoring.domain.model.SessionState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class SessionViewModel(
    tokenStore: TokenStore,
    private val handle: SavedStateHandle
) : ViewModel() {

    val state: StateFlow<SessionState> =
        tokenStore.flow()
            .map { token -> if (token == null) SessionState.Guest else SessionState.Authed }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = SessionState.Loading
            )
}