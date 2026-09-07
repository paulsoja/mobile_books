package com.spasinnya.mentoring.presentation.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.parameter.ParametersHolder

fun <T> Flow<T>.withLoading(
    setLoading: (Boolean) -> Unit
): Flow<T> =
    onStart { setLoading(true) }
        .onCompletion { setLoading(false) }

@Composable
fun<T> Flow<T>.CollectEffects(effects: (T) -> Unit) =
    LaunchedEffect(Unit) {
        collect { effect ->
            effects.invoke(effect)
        }
    }

@Composable
fun OnScreenResumed(onResumed: () -> Unit) {
    LifecycleResumeEffect(Unit) {
        onResumed()
        onPauseOrDispose { }
    }
}

@Composable
inline fun <reified VM : ViewModel, S, E> rememberScreenModel(
    crossinline parameters: () -> ParametersHolder = { parametersOf() },
    crossinline getState: (VM) -> StateFlow<S> = { (it as BaseMviViewModel<S, *, E>).state },
    crossinline getEffect: (VM) -> Flow<E> = { (it as BaseMviViewModel<S, *, E>).effect },
    noinline onEffect: (E) -> Unit = {},
): Pair<VM, S> {

    val viewModel: VM = koinViewModel(parameters = { parameters() })

    val state by getState(viewModel).collectAsStateWithLifecycle()

    getEffect(viewModel).CollectEffects { effect ->
        onEffect(effect)
    }

    return viewModel to state
}


inline fun <E, A, B> Flow<Validated<E, A>>.mapValid(
    crossinline transform: suspend (A) -> B
): Flow<Validated<E, B>> =
    map { validated ->
        when (validated) {
            is Validated.Valid -> Validated.Valid(transform(validated.value))
            is Validated.Invalid -> validated
        }
    }

inline fun <E, A> Flow<Validated<E, A>>.alsoValidDo(
    crossinline action: suspend (A) -> Unit
): Flow<Validated<E, A>> =
    onEach { validated ->
        if (validated is Validated.Valid) {
            action(validated.value)
        }
    }