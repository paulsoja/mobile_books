package com.spasinnya.mentoring.presentation.screens.homeflow.home

import com.spasinnya.mentoring.presentation.base.BaseMviViewModel

class HomeViewModel() : BaseMviViewModel<HomeContract.State, HomeContract.Event, HomeContract.Effect>() {

    override fun createInitialState(): HomeContract.State = HomeContract.State()

    override fun handleEvent(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.ToggleSettingsDialog -> setState { copy(showSettingsDialog = event.show) }
            is HomeContract.Event.OnLanguageChosen -> setState { copy(selectedLanguage = event.language) }
        }
    }
}