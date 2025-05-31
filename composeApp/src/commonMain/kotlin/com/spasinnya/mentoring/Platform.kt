package com.spasinnya.mentoring

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform