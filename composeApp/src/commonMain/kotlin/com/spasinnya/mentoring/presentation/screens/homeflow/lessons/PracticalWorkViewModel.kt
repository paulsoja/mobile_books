package com.spasinnya.mentoring.presentation.screens.homeflow.lessons

import androidx.lifecycle.viewModelScope
import com.spasinnya.mentoring.domain.model.HomeworkLesson
import com.spasinnya.mentoring.domain.usecase.books.GetHomeworkAnswersUseCase
import com.spasinnya.mentoring.domain.usecase.books.ObserveHomeworkUseCase
import com.spasinnya.mentoring.domain.usecase.books.SaveHomeworkAnswersUseCase
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
    private val lessonNumber: Int,
    private val observeHomeworkUseCase: ObserveHomeworkUseCase,
    private val getHomeworkAnswersUseCase: GetHomeworkAnswersUseCase,
    private val saveHomeworkAnswersUseCase: SaveHomeworkAnswersUseCase,
) : BaseMviViewModel<PracticalWorkContract.State, PracticalWorkContract.Event, PracticalWorkContract.Effect>(
    initialState = PracticalWorkContract.State(isLoading = true)
) {

    override fun handleEvent(event: PracticalWorkContract.Event) {
        when (event) {
            is PracticalWorkContract.Event.ToggleOption -> setState {
                copy(checkedOptions = checkedOptions + (event.optionId to event.checked))
            }

            is PracticalWorkContract.Event.SelectOption -> setState {
                copy(selectedOptions = selectedOptions + (event.questionId to event.optionId))
            }

            is PracticalWorkContract.Event.ChangeText -> setState {
                copy(textAnswers = textAnswers + (event.key to event.value))
            }

            PracticalWorkContract.Event.Save -> saveAnswers()
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

                    is Validated.Valid -> {
                        setState {
                            copy(
                                lessons = result.value.lessons,
                                isLoading = false,
                                isError = false,
                            )
                        }
                        result.value.lessons
                            .firstOrNull { it.lessonNumber == lessonNumber }
                            ?.let { loadSavedAnswers(it) }
                    }
                }
            }
    }

    private fun loadSavedAnswers(lesson: HomeworkLesson) = viewModelScope.launch(Dispatchers.IO) {
        getHomeworkAnswersUseCase.invoke(bookId, weekNumber, lessonNumber)
            .catch { Napier.d { "getHomeworkAnswers: catch=${it.message}" } }
            .collectLatest { result ->
                when (result) {
                    is Validated.Invalid ->
                        Napier.d { "getHomeworkAnswers: error=${result.error}" }

                    is Validated.Valid -> {
                        val parsed = result.value.toHomeworkAnswerState(lesson)
                        setState {
                            copy(
                                checkedOptions = parsed.checkedOptions,
                                selectedOptions = parsed.selectedOptions,
                                textAnswers = parsed.textAnswers,
                            )
                        }
                    }
                }
            }
    }

    private fun saveAnswers() = viewModelScope.launch(Dispatchers.IO) {
        val state = state.value
        val lesson = state.lessons.firstOrNull { it.lessonNumber == lessonNumber }
        if (lesson == null) {
            sendEffect { PracticalWorkContract.Effect.SaveFailed }
            return@launch
        }

        val answers = buildHomeworkAnswers(
            lesson = lesson,
            checkedOptions = state.checkedOptions,
            selectedOptions = state.selectedOptions,
            textAnswers = state.textAnswers,
        )

        setState { copy(isSaving = true) }

        saveHomeworkAnswersUseCase.invoke(bookId, weekNumber, lessonNumber, answers)
            .catch {
                Napier.d { "saveHomeworkAnswers: catch=${it.message}" }
                setState { copy(isSaving = false) }
                sendEffect { PracticalWorkContract.Effect.SaveFailed }
            }
            .collectLatest { result ->
                Napier.d { "saveHomeworkAnswers: collect=$result" }
                setState { copy(isSaving = false) }
                when (result) {
                    is Validated.Valid -> sendEffect { PracticalWorkContract.Effect.Saved }
                    is Validated.Invalid -> sendEffect { PracticalWorkContract.Effect.SaveFailed }
                }
            }
    }
}
