package com.spasinnya.mentoring.presentation.screens.homeflow.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.spasinnya.mentoring.domain.usecase.auth.LogoutUseCase
import com.spasinnya.mentoring.presentation.base.BaseMviViewModel
import com.spasinnya.mentoring.presentation.di.resetAppGraph
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

class HomeViewModel(
    private val logoutUseCase: LogoutUseCase,
    private val savedStateHandle: SavedStateHandle
) : BaseMviViewModel<HomeContract.State, HomeContract.Event, HomeContract.Effect>() {

    override fun createInitialState(): HomeContract.State = HomeContract.State()

    override fun handleEvent(event: HomeContract.Event) {
        when (event) {
            HomeContract.Event.Logout -> sendEffect { HomeContract.Effect.NavigateToLogin }
        }
    }

    fun logout() = viewModelScope.launch {
        logoutUseCase.invoke()
            .catch {  }
            .onCompletion {
                resetAppGraph()
                dispatchEvent(HomeContract.Event.Logout)
            }
            .collect()
    }
}