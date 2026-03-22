package com.spasinnya.mentoring.presentation.base

sealed class Validated<out E, out V> {
    data class Valid<V>(val value: V) : Validated<Nothing, V>()
    data class Invalid<E>(val error: E) : Validated<E, Nothing>()
}

inline fun <E, A, B> Validated<E, A>.map(
    transform: (A) -> B
): Validated<E, B> =
    when (this) {
        is Validated.Valid -> Validated.Valid(transform(value))
        is Validated.Invalid -> Validated.Invalid(error)
    }

inline fun <E, V, T> Validated<E, V>.fold(
    onInvalid: (Validated.Invalid<E>) -> T,
    onValid: (Validated.Valid<V>) -> T
): T = when (this) {
    is Validated.Valid -> onValid(this)
    is Validated.Invalid -> onInvalid(this)
}

fun <E, E2, V> Validated<E, V>.mapErrors(
    transform: (E) -> E2
): Validated<E2, V> = when (this) {
    is Validated.Valid -> Validated.Valid(value)
    is Validated.Invalid -> Validated.Invalid(transform(error))
}
