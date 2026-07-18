package com.spasinnya.mentoring.presentation.screens.homeflow.lessons

import androidx.lifecycle.viewModelScope
import com.spasinnya.mentoring.domain.usecase.books.ObserveBookUseCase
import com.spasinnya.mentoring.domain.usecase.books.ObserveHomeworkUseCase
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
    private val observeHomeworkUseCase: ObserveHomeworkUseCase,
) : BaseMviViewModel<LessonsContract.State, LessonsContract.Event, LessonsContract.Effect>(initialState = LessonsContract.State(weekNumber = weekNumber)) {

    override fun handleEvent(event: LessonsContract.Event) {
        when (event) {
            is LessonsContract.Event.OpenPracticalWork -> setState {
                copy(practicalWorkLesson = event.lessonNumber)
            }

            is LessonsContract.Event.ClosePracticalWork -> setState {
                copy(practicalWorkLesson = null)
            }
        }
    }

    init {
        loadLessons()
        loadHomeworkAvailability()
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
}