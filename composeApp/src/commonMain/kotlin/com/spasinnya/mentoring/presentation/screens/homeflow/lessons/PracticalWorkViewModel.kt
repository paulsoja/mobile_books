package com.spasinnya.mentoring.presentation.screens.homeflow.lessons

import androidx.lifecycle.viewModelScope
import com.spasinnya.mentoring.domain.usecase.books.ObserveHomeworkUseCase
import com.spasinnya.mentoring.presentation.base.BaseMviViewModel
import com.spasinnya.mentoring.presentation.base.Validated
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PracticalWorkViewModel(
    private val bookId: String,
    private val weekNumber: Int,
    private val observeHomeworkUseCase: ObserveHomeworkUseCase,
) : BaseMviViewModel<PracticalWorkContract.State, PracticalWorkContract.Event, PracticalWorkContract.Effect>(
    initialState = PracticalWorkContract.State(isLoading = true)
) {

    override fun handleEvent(event: PracticalWorkContract.Event) {
        when (event) {
            is PracticalWorkContract.Event.ToggleOption -> setState {
                copy(checkedOptions = checkedOptions + (event.optionId to event.checked))
            }

            is PracticalWorkContract.Event.ChangeText -> setState {
                copy(textAnswers = textAnswers + (event.key to event.value))
            }
        }
    }

    init {
        loadHomework()
    }

    private fun loadHomework() = viewModelScope.launch(Dispatchers.IO) {
        observeHomeworkUseCase.invoke(bookId, weekNumber)
            .catch {
                Napier.d { "observeHomeworkUseCase: catch=${it.message}" }
                setState { copy(isLoading = false, isError = true) }
            }
            .collectLatest { result ->
                when (result) {
                    is Validated.Invalid -> setState {
                        copy(isLoading = false, isError = true)
                    }

                    is Validated.Valid -> setState {
                        copy(
                            lessons = result.value.lessons,
                            isLoading = false,
                            isError = false,
                        )
                    }
                }
            }
    }
}
