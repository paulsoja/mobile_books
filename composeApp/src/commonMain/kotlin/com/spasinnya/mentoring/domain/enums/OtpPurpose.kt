package com.spasinnya.mentoring.domain.enums

enum class OtpPurpose {
    LOGIN,
    PASSWORD_RESET;

    companion object {
        val default: OtpPurpose = LOGIN

        fun fromName(name: String?, default: OtpPurpose = Companion.default): OtpPurpose =
            entries.firstOrNull { it.name == name } ?: default
    }
}
