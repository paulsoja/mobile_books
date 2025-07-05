package com.spasinnya.mentoring.presentation.screens.homeflow.weeks

import com.spasinnya.mentoring.presentation.base.BaseMviViewModel

class WeeksViewModel : BaseMviViewModel<WeeksContract.State, WeeksContract.Event, WeeksContract.Effect>() {

    override fun createInitialState(): WeeksContract.State = WeeksContract.State()

    override fun handleEvent(event: WeeksContract.Event) {
        when (event) {

            else -> Unit
        }
    }
}