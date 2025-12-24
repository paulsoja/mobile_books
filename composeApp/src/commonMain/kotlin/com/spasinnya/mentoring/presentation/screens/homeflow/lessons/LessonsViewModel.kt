package com.spasinnya.mentoring.presentation.screens.homeflow.lessons

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.spasinnya.mentoring.domain.usecase.books.GetLessonsUseCase
import com.spasinnya.mentoring.presentation.base.BaseMviViewModel
import com.spasinnya.mentoring.presentation.base.Validated
import com.spasinnya.mentoring.presentation.base.withLoading
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LessonsViewModel(
    private val weekId: Int,
    private val lessonsUseCase: GetLessonsUseCase,
    private val savedStateHandle: SavedStateHandle
) : BaseMviViewModel<LessonsContract.State, LessonsContract.Event, LessonsContract.Effect>(initialState = LessonsContract.State()) {

    override fun handleEvent(event: LessonsContract.Event) {
        when (event) {

            else -> {}
        }
    }

    init {
        loadLessons()
    }

    private fun loadLessons() = viewModelScope.launch(Dispatchers.IO) {
        lessonsUseCase.invoke(weekId)
            .withLoading { loading -> setState { copy(isLoading = loading) } }
            .collectLatest { result ->
                when (result) {
                    is Validated.Invalid -> handleDomainErrors(
                        errors = result.errors,
                        reduce = { errorType ->
                            copy(
                                isLoading = false,
                            )
                        }
                    )

                    is Validated.Valid -> setState { copy(lessons = lessons) }
                }
            }
    }
}