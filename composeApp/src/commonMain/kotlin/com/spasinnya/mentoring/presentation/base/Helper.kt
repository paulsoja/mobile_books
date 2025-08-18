package com.spasinnya.mentoring.presentation.base

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

fun<T> Flow<T>.loader(isLoading: (Boolean) -> Unit): Flow<T> = this
    .onStart { isLoading(true) }
    .onCompletion { isLoading(false) }