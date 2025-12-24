package com.spasinnya.mentoring.presentation.base

sealed class Validated<out E, out V> {
    data class Valid<V>(val value: V) : Validated<Nothing, V>()
    data class Invalid<E>(val errors: List<E>) : Validated<E, Nothing>()
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
    is Validated.Invalid -> Validated.Invalid(errors.map(transform))
}

fun <E, V1, V2, R> Validated<E, V1>.zip(
    other: Validated<E, V2>,
    combine: (V1, V2) -> R
): Validated<E, R> = when {
    this is Validated.Valid && other is Validated.Valid -> Validated.Valid(combine(this.value, other.value))
    this is Validated.Invalid && other is Validated.Invalid -> Validated.Invalid(this.errors + other.errors)
    this is Validated.Invalid -> this
    other is Validated.Invalid -> other
    else -> error("Unreachable")
}
