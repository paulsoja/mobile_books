package com.spasinnya.mentoring.presentation.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

fun<T> Flow<T>.loader(isLoading: (Boolean) -> Unit): Flow<T> = this
    .onStart { isLoading(true) }
    .onCompletion { isLoading(false) }

@Composable
fun<T> Flow<T>.CollectEffects(effects: (T) -> Unit) =
    LaunchedEffect(Unit) {
        collect { effect ->
            effects.invoke(effect)
        }
    }