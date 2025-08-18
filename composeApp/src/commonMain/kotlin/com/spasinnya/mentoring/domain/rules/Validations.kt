package com.spasinnya.mentoring.domain.rules

fun isValid(value: String, regex: RegexType): Boolean = regex.regex().matches(value)

enum class RegexType(val regex: () -> Regex) {
    EMAIL(emailRegex),
    PASSWORD(passwordRegex)
}

private val emailRegex = { "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex() }
private val passwordRegex = { "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$".toRegex() }

sealed class Validated<out E, out A> {
    data class Valid<A>(val value: A) : Validated<Nothing, A>()
    data class Invalid<E>(val errors: List<E>) : Validated<E, Nothing>()
}

inline fun <E, A, B, C> Validated<E, A>.zip(
    other: Validated<E, B>,
    combine: (A, B) -> C
): Validated<E, C> = when (this) {
    is Validated.Valid -> when (other) {
        is Validated.Valid -> Validated.Valid(combine(this.value, other.value))
        is Validated.Invalid -> Validated.Invalid(other.errors)
    }
    is Validated.Invalid -> when (other) {
        is Validated.Valid -> Validated.Invalid(this.errors)
        is Validated.Invalid -> Validated.Invalid(this.errors + other.errors)
    }
}

inline fun <E, E2, A> Validated<E, A>.mapErrors(transform: (E) -> E2): Validated<E2, A> =
    when (this) {
        is Validated.Valid -> this
        is Validated.Invalid -> Validated.Invalid(this.errors.map(transform))
    }