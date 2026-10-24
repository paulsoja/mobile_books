package com.spasinnya.mentoring.presentation.screens.homeflow.lessons

import androidx.lifecycle.viewModelScope
import com.spasinnya.mentoring.domain.model.HomeworkAnswer
import com.spasinnya.mentoring.domain.usecase.books.SaveHomeworkAnswersUseCase
import com.spasinnya.mentoring.domain.usecase.books.ObserveBookUseCase
import com.spasinnya.mentoring.domain.usecase.books.GetCompletedLessonsUseCase
import com.spasinnya.mentoring.domain.usecase.books.ObserveHomeworkUseCase
import com.spasinnya.mentoring.presentation.base.BaseMviViewModel
import com.spasinnya.mentoring.presentation.base.Validated
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LessonsViewModel(
    private val bookId: String,
    private val weekNumber: Int,
    private val importBookFromMarkdownUseCase: ObserveBookUseCase,
    private val observeHomeworkUseCase: ObserveHomeworkUseCase,
    private val getCompletedLessonsUseCase: GetCompletedLessonsUseCase,
    private val saveHomeworkAnswersUseCase: SaveHomeworkAnswersUseCase,
) : BaseMviViewModel<LessonsContract.State, LessonsContract.Event, LessonsContract.Effect>(initialState = LessonsContract.State(weekNumber = weekNumber)) {

    override fun handleEvent(event: LessonsContract.Event) {
        when (event) {
            is LessonsContract.Event.ConfirmLesson -> confirmLesson(event.lessonNumber)
            is LessonsContract.Event.OpenPracticalWork -> setState {
                copy(practicalWorkLesson = event.lessonNumber)
            }

            is LessonsContract.Event.ClosePracticalWork -> setState {
                copy(practicalWorkLesson = null)
            }
            is LessonsContract.Event.UpdateCompletedLessons -> setState {
                copy(completedLessons = event.completedLessons)
            }
        }
    }

    init {
        loadLessons()
        loadHomeworkAvailability()
        loadCompletedLessons()
    }

    private fun confirmLesson(lessonNumber: Int) {
        if (state.value.isConfirmingLesson) return
        setState { copy(isConfirmingLesson = true) }
        viewModelScope.launch {
            try {
                // The server counts stored question answers as completed lessons.
                // An empty answers list would not persist completion.
                saveHomeworkAnswersUseCase(
                    bookId = bookId,
                    weekNumber = weekNumber,
                    lessonNumber = lessonNumber,
                    answers = listOf(
                        HomeworkAnswer(
                            questionId = "w${weekNumber}_l${lessonNumber}_confirmation",
                            values = emptyList(),
                        )
                    ),
                ).collectLatest { result ->
                    when (result) {
                        is Validated.Valid -> setState { copy(completedLessons = result.value) }
                        is Validated.Invalid -> Napier.d { "confirmLesson: error=${result.error}" }
                    }
                }
            } catch (exception: CancellationException) {
                throw exception
            } catch (exception: Exception) {
                Napier.d { "confirmLesson: catch=${exception.message}" }
            } finally {
                setState { copy(isConfirmingLesson = false) }
            }
        }
    }

    private fun loadLessons() = viewModelScope.launch(Dispatchers.IO) {
        importBookFromMarkdownUseCase.invoke(bookId, weekNumber)
            .catch {
                Napier.d { "importBookFromMarkdownUseCase: catch=${it.message}" }
            }
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

                    is Validated.Valid -> setState {
                        copy(
                            lessons = result.value.lessons,
                            weekTitle = result.value.weekTitle
                        )
                    }
                }
            }
    }

    /**
     * Determines which lessons in this week actually have practical-work content,
     * so the screen can label the action button accordingly. When the homework
     * file is missing or empty, no lesson is treated as having homework.
     */
    private fun loadHomeworkAvailability() = viewModelScope.launch(Dispatchers.IO) {
        observeHomeworkUseCase.invoke(bookId, weekNumber)
            .catch {
                Napier.d { "observeHomeworkUseCase: catch=${it.message}" }
                setState { copy(lessonsWithHomework = emptySet()) }
            }
            .collectLatest { result ->
                val lessonNumbers = when (result) {
                    is Validated.Invalid -> emptySet()
                    is Validated.Valid -> result.value.lessons
                        .filter { it.questions.isNotEmpty() }
                        .map { it.lessonNumber }
                        .toSet()
                }
                setState { copy(lessonsWithHomework = lessonNumbers) }
            }
    }

    private fun loadCompletedLessons() = viewModelScope.launch(Dispatchers.IO) {
        getCompletedLessonsUseCase.invoke(bookId, weekNumber)
            .catch {
                Napier.d { "getCompletedLessonsUseCase: catch=${it.message}" }
            }
            .collectLatest { result ->
                when (result) {
                    is Validated.Invalid -> Unit
                    is Validated.Valid -> {
                        Napier.d { "getCompletedLessonsUseCase: valid=${result.value.joinToString(separator = " | ")}" }
                        setState {
                            copy(completedLessons = result.value)
                        }
                    }
                }
            }
    }
}