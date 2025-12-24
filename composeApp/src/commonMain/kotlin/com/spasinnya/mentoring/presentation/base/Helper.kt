package com.spasinnya.mentoring.presentation.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.spasinnya.mentoring.presentation.di.AppGraph
import com.spasinnya.mentoring.presentation.di.viewModelFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

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
inline fun <reified VM : ViewModel, S, E> rememberScreenModel(
    crossinline create: (graph: AppGraph, handle: SavedStateHandle) -> VM,
    crossinline getState: (VM) -> StateFlow<S>,
    crossinline getEffect: (VM) -> Flow<E>,
    crossinline onEffect: (E) -> Unit,
): Pair<VM, S> {

    val factory = remember {
        viewModelFactory { graph, handle ->
            create(graph, handle)
        }
    }

    val viewModel: VM = viewModel(factory = factory)

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