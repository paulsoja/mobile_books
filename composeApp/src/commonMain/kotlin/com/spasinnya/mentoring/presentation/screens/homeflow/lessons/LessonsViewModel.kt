package com.spasinnya.mentoring.presentation.screens.homeflow.lessons

import com.spasinnya.mentoring.presentation.base.BaseMviViewModel

class LessonsViewModel() : BaseMviViewModel<LessonsContract.State, LessonsContract.Event, LessonsContract.Effect>() {

    override fun createInitialState(): LessonsContract.State = LessonsContract.State()

    override fun handleEvent(event: LessonsContract.Event) {
        when (event) {

            else -> Unit
        }
    }
}