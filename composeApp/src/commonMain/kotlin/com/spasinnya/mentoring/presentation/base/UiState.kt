package com.spasinnya.mentoring.presentation.base

sealed interface UiState<out T> {
    data object Absent : UiState<Nothing>
    data class Present<T>(val data: T) : UiState<T>
}

val <T> UiState<T>.isPresent: Boolean
    get() = this is UiState.Present

val <T> UiState<T>.isAbsent: Boolean
    get() = this === UiState.Absent

inline fun <T, R> UiState<T>.fold(
    onAbsent: () -> R,
    onPresent: (T) -> R,
): R = when (this) {
    UiState.Absent -> onAbsent()
    is UiState.Present -> onPresent(data)
}

inline fun <T, R> UiState<T>.map(transform: (T) -> R): UiState<R> = when (this) {
    UiState.Absent -> UiState.Absent
    is UiState.Present -> UiState.Present(transform(data))
}

inline fun <T, R> UiState<T>.flatMap(transform: (T) -> UiState<R>): UiState<R> = when (this) {
    UiState.Absent -> UiState.Absent
    is UiState.Present -> transform(data)
}

inline fun <T> UiState<T>.getOrElse(default: () -> T): T = when (this) {
    UiState.Absent -> default()
    is UiState.Present -> data
}

inline fun <T> UiState<T>.onPresent(block: (T) -> Unit): UiState<T> = apply {
    if (this is UiState.Present) block(data)
}

inline fun <T> UiState<T>.onAbsent(block: () -> Unit): UiState<T> = apply {
    if (this === UiState.Absent) block()
}

inline fun <T> UiState<T>.fromNullable(value: T?): UiState<T> =
    if (value == null) UiState.Absent else UiState.Present(value)