package com.spasinnya.mentoring.presentation.screens.homeflow.lessons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spasinnya.mentoring.domain.mapper.asAnnotatedString
import com.spasinnya.mentoring.domain.model.HomeworkLesson
import com.spasinnya.mentoring.domain.model.HomeworkOption
import com.spasinnya.mentoring.domain.model.HomeworkQuestion
import com.spasinnya.mentoring.domain.model.HomeworkTextSegment
import com.spasinnya.mentoring.domain.model.RichParagraph
import com.spasinnya.mentoring.generated.resources.Res
import com.spasinnya.mentoring.generated.resources.common_confirm
import com.spasinnya.mentoring.generated.resources.lessons_lesson
import com.spasinnya.mentoring.generated.resources.lessons_practical_work
import com.spasinnya.mentoring.presentation.base.rememberScreenModel
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreCircularProgressIndicator
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreModalTopBar
import com.spasinnya.mentoring.presentation.designsystem.composable.CorePrimaryButton
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVerticalLarge
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextBody
import com.spasinnya.mentoring.presentation.designsystem.composable.modal.CoreModalBottomSheet
import com.spasinnya.mentoring.presentation.designsystem.composable.question.CoreQuestion
import com.spasinnya.mentoring.presentation.designsystem.composable.question.CoreQuestionCheckbox
import com.spasinnya.mentoring.presentation.designsystem.composable.question.CoreQuestionCheckboxInput
import com.spasinnya.mentoring.presentation.designsystem.composable.question.CoreQuestionInput
import com.spasinnya.mentoring.presentation.designsystem.composable.question.CoreQuestionRadioButton
import com.spasinnya.mentoring.presentation.designsystem.composable.question.CoreQuestionTextInput
import com.spasinnya.mentoring.presentation.designsystem.composable.question.CoreQuestionTextRadioButton
import com.spasinnya.mentoring.presentation.designsystem.composable.question.QuestionOption
import com.spasinnya.mentoring.presentation.designsystem.composable.question.QuestionRadioRow
import com.spasinnya.mentoring.presentation.designsystem.composable.question.QuestionSegment
import org.jetbrains.compose.resources.stringResource
import org.koin.core.parameter.parametersOf
import kotlin.jvm.JvmName

@Composable
fun PracticalWorkModalBottomSheet(
    bookId: String,
    weekNumber: Int,
    lessonNumber: Int,
    onClose: () -> Unit,
    onSaved: (List<Int>) -> Unit = {},
) {
    CoreModalBottomSheet<PracticalWorkSheetAction>(
        onDismissed = onClose,
        onAction = { /* handled per-action inside the sheet content */ },
        shouldDismissOnAction = { it is PracticalWorkSheetDismissAction }
    ) {
        val (viewModel, state) = rememberScreenModel<PracticalWorkViewModel, PracticalWorkContract.State, PracticalWorkContract.Effect>(
            parameters = { parametersOf(bookId, weekNumber, lessonNumber) },
            onEffect = { effect ->
                when (effect) {
                    is PracticalWorkContract.Effect.Saved -> {
                        onSaved(effect.completedLessons)
                        dismiss()
                    }
                    PracticalWorkContract.Effect.SaveFailed -> Unit
                }
            },
        )

        PracticalWorkModalBottomSheetContent(
            isLoading = state.isLoading,
            lesson = state.lessons.firstOrNull { it.lessonNumber == lessonNumber },
            checkedOptions = state.checkedOptions,
            selectedOptions = state.selectedOptions,
            textAnswers = state.textAnswers,
            onEvent = viewModel::dispatchEvent,
            onAction = { action ->
                when (action) {
                    PracticalWorkClose -> send(action)
                    PracticalWorkConfirm -> viewModel.dispatchEvent(PracticalWorkContract.Event.Save)
                }
            },
        )
    }
}

@Composable
private fun PracticalWorkModalBottomSheetContent(
    isLoading: Boolean,
    lesson: HomeworkLesson?,
    checkedOptions: Map<String, Boolean>,
    selectedOptions: Map<String, String>,
    textAnswers: Map<String, String>,
    onEvent: (PracticalWorkContract.Event) -> Unit,
    onAction: (PracticalWorkSheetAction) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
    ) {
        CoreModalTopBar(
            title = stringResource(Res.string.lessons_practical_work),
            onCloseClick = { onAction(PracticalWorkClose) }
        )

        CoreSpacerVerticalLarge()

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxWidth().height(240.dp),
                contentAlignment = Alignment.Center
            ) {
                CoreCircularProgressIndicator()
            }
        } else {
            lesson?.let {
                LessonSection(
                    lesson = it,
                    checkedOptions = checkedOptions,
                    selectedOptions = selectedOptions,
                    textAnswers = textAnswers,
                    onEvent = onEvent,
                )
            }

            CoreSpacerVerticalLarge()

            CorePrimaryButton(
                text = stringResource(Res.string.common_confirm),
                onClick = { onAction(PracticalWorkConfirm) },
            )
        }

        CoreSpacerVerticalLarge()
    }
}

@Composable
private fun LessonSection(
    lesson: HomeworkLesson,
    checkedOptions: Map<String, Boolean>,
    selectedOptions: Map<String, String>,
    textAnswers: Map<String, String>,
    onEvent: (PracticalWorkContract.Event) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        CoreTextBody(
            text = "${stringResource(Res.string.lessons_lesson)} ${lesson.lessonNumber}",
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3C4E73),
                fontSize = 18.sp,
            ),
        )

        lesson.questions.forEach { question ->
            HomeworkQuestionItem(
                question = question,
                checkedOptions = checkedOptions,
                selectedOptions = selectedOptions,
                textAnswers = textAnswers,
                onEvent = onEvent,
            )
        }
    }
}

@Composable
private fun HomeworkQuestionItem(
    question: HomeworkQuestion,
    checkedOptions: Map<String, Boolean>,
    selectedOptions: Map<String, String>,
    textAnswers: Map<String, String>,
    onEvent: (PracticalWorkContract.Event) -> Unit,
) {
    when (question) {
        is HomeworkQuestion.Checkbox -> CoreQuestionCheckbox(
            question = question.question.rich(),
            description = question.description?.rich(),
            options = question.options.toOptions(question.id, checkedOptions),
            onOptionToggle = { option ->
                onEvent(PracticalWorkContract.Event.ToggleOption(option.id, option.checked))
            },
        )

        is HomeworkQuestion.RadioButton -> CoreQuestionRadioButton(
            question = question.question.rich(),
            description = question.description?.rich(),
            options = question.options.toRadioOptions(question.id, selectedOptions),
            onOptionSelect = { option ->
                onEvent(PracticalWorkContract.Event.SelectOption(question.id, option.id))
            },
        )

        is HomeworkQuestion.CheckboxInput -> CoreQuestionCheckboxInput(
            question = question.question.rich(),
            description = question.description?.rich(),
            options = question.options.toOptions(question.id, checkedOptions),
            onOptionToggle = { option ->
                onEvent(PracticalWorkContract.Event.ToggleOption(option.id, option.checked))
            },
            value = textAnswers[question.id].orEmpty(),
            onValueChange = { onEvent(PracticalWorkContract.Event.ChangeText(question.id, it)) },
        )

        is HomeworkQuestion.Input -> CoreQuestionInput(
            question = question.question.rich(),
            description = question.description?.rich(),
            value = textAnswers[question.id].orEmpty(),
            onValueChange = { onEvent(PracticalWorkContract.Event.ChangeText(question.id, it)) },
        )

        is HomeworkQuestion.TextInput -> CoreQuestionTextInput(
            question = question.question.rich(),
            segments = question.segments.map { segment ->
                when (segment) {
                    is HomeworkTextSegment.Text -> QuestionSegment.Text(segment.text.rich())
                    is HomeworkTextSegment.Input -> {
                        val key = "${question.id}_i${segment.index}"
                        QuestionSegment.Input(
                            value = textAnswers[key].orEmpty(),
                            onValueChange = { onEvent(PracticalWorkContract.Event.ChangeText(key, it)) },
                        )
                    }
                    is HomeworkTextSegment.NumberInput -> {
                        val key = "${question.id}_i${segment.index}"
                        QuestionSegment.NumberInput(
                            value = textAnswers[key].orEmpty(),
                            onValueChange = { onEvent(PracticalWorkContract.Event.ChangeText(key, it)) },
                        )
                    }
                }
            },
        )

        is HomeworkQuestion.Text -> CoreQuestion(
            question = question.question.rich(),
            description = question.paragraphs.joinRich(),
        )

        is HomeworkQuestion.Open -> CoreQuestion(
            question = question.question.rich(),
            description = question.description?.rich(),
        )

        is HomeworkQuestion.TextRadioButton -> CoreQuestionTextRadioButton(
            question = question.question.rich(),
            rows = question.items.mapIndexed { rowIndex, item ->
                val rowId = "${question.id}_r$rowIndex"
                QuestionRadioRow(
                    id = rowId,
                    prompt = item.prompt.rich(),
                    options = item.options.toRadioOptions(rowId, selectedOptions),
                )
            },
            onOptionSelect = { rowId, option ->
                onEvent(PracticalWorkContract.Event.SelectOption(rowId, option.id))
            },
        )
    }
}

private fun RichParagraph.rich(): AnnotatedString =
    asAnnotatedString(highlightColor = HomeworkHighlightColor)

private fun List<RichParagraph>.joinRich(): AnnotatedString =
    buildAnnotatedString {
        this@joinRich.forEachIndexed { index, paragraph ->
            if (index > 0) append("\n\n")
            append(paragraph.rich())
        }
    }

private fun List<HomeworkOption>.toOptions(
    questionId: String,
    checkedOptions: Map<String, Boolean>,
): List<QuestionOption> = map { option ->
    val optionId = "${questionId}_${option.id}"
    QuestionOption(
        id = optionId,
        label = option.text.rich(),
        checked = checkedOptions[optionId] == true,
    )
}

@JvmName("homeworkOptionsToRadioOptions")
private fun List<HomeworkOption>.toRadioOptions(
    questionId: String,
    selectedOptions: Map<String, String>,
): List<QuestionOption> = map { option ->
    val optionId = "${questionId}_${option.id}"
    QuestionOption(
        id = optionId,
        label = option.text.rich(),
        checked = selectedOptions[questionId] == optionId,
    )
}

@JvmName("richParagraphsToRadioOptions")
private fun List<RichParagraph>.toRadioOptions(
    questionId: String,
    selectedOptions: Map<String, String>,
): List<QuestionOption> = mapIndexed { index, label ->
    val optionId = "${questionId}_o$index"
    QuestionOption(
        id = optionId,
        label = label.rich(),
        checked = selectedOptions[questionId] == optionId,
    )
}

private val HomeworkHighlightColor = Color(0xFFFFF3B0)
