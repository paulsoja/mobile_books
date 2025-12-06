package com.spasinnya.mentoring.presentation.screens.homeflow.lessons

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.spasinnya.mentoring.domain.usecase.books.GetLessonsUseCase
import com.spasinnya.mentoring.presentation.base.BaseMviViewModel
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class LessonsViewModel(
    private val weekId: Int,
    private val lessonsUseCase: GetLessonsUseCase,
    private val savedStateHandle: SavedStateHandle
) : BaseMviViewModel<LessonsContract.State, LessonsContract.Event, LessonsContract.Effect>() {

    override fun createInitialState(): LessonsContract.State = LessonsContract.State()

    override fun handleEvent(event: LessonsContract.Event) {
        when (event) {
            is LessonsContract.Event.LoadedLessons -> setState { copy(lessons = event.lessons) }
            is LessonsContract.Event.ShowLoading -> setState { copy(isLoading = event.show) }
        }
    }

    init {
        loadLessons()
    }

    private fun loadLessons() = viewModelScope.launch(Dispatchers.IO) {
        lessonsUseCase.invoke(weekId)
            .catch {
                Napier.d("loadWeeks: catch=$it")
            }
            .onStart { dispatchEvent(LessonsContract.Event.ShowLoading(true)) }
            .onCompletion { dispatchEvent(LessonsContract.Event.ShowLoading(false)) }
            .collectLatest { lessons ->
                dispatchEvent(LessonsContract.Event.LoadedLessons(lessons))
            }
    }
}