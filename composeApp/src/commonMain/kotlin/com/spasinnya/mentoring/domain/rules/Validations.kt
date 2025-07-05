package com.spasinnya.mentoring.domain.rules

fun isValid(value: String, regex: RegexType): Boolean = regex.regex().matches(value)

enum class RegexType(val regex: () -> Regex) {
    EMAIL(emailRegex),
    PASSWORD(passwordRegex)
}

private val emailRegex = { "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex() }
private val passwordRegex = { "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$".toRegex() }