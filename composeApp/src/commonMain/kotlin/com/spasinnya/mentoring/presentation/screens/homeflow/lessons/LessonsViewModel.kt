package com.spasinnya.mentoring.presentation.screens.homeflow.lessons

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.spasinnya.mentoring.domain.usecase.books.ObserveBookUseCase
import com.spasinnya.mentoring.presentation.base.BaseMviViewModel
import com.spasinnya.mentoring.presentation.base.Validated
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LessonsViewModel(
    private val bookId: String,
    private val weekNumber: Int,
    private val importBookFromMarkdownUseCase: ObserveBookUseCase,
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
        importBookFromMarkdownUseCase.invoke(bookId, weekNumber)
            .catch {
                Napier.d { "importBookFromMarkdownUseCase: catch=${it.message}" }
            }
            .collectLatest { result ->
                Napier.d { "importBookFromMarkdownUseCase: collect=${result}" }
                Napier.d { "importBookFromMarkdownUseCase: weekId=$weekNumber" }
                when (result) {
                    is Validated.Invalid -> handleDomainErrors(
                        error = result.error,
                        reduce = { errorType ->
                            copy(
                                isLoading = false,
                            )
                        }
                    )

                    is Validated.Valid -> setState { copy(lessons = result.value.lessons) }
                }
            }


        /*lessonsUseCase.invoke(weekId)
            .withLoading { loading -> setState { copy(isLoading = loading) } }
            .collectLatest { result ->
                when (result) {
                    is Validated.Invalid -> handleDomainErrors(
                        error = result.error,
                        reduce = { errorType ->
                            copy(
                                isLoading = false,
                            )
                        }
                    )

                    is Validated.Valid -> setState { copy(lessons = result.value) }
                }
            }*/
    }
}