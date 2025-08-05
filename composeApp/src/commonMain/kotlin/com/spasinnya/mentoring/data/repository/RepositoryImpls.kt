package com.spasinnya.mentoring.data.repository

import com.spasinnya.mentoring.data.net.httpClient
import com.spasinnya.mentoring.domain.model.AuthToken
import com.spasinnya.mentoring.domain.repository.LoginRepository
import com.spasinnya.mentoring.domain.repository.OtpRepository
import com.spasinnya.mentoring.domain.repository.RegisterRepository
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.flow

val loginRepo: LoginRepository = { creds ->
    httpClient.post("/login") {
        setBody(creds)
    }.body<AuthToken>()
}

val registerRepo: RegisterRepository = { creds ->
    flow<String> {
        val response = httpClient.post("/register") {
            setBody(creds)
        }

        if (response.status.value in 200..299) {
            emit("Success")
        }
    }

    //response.status.value in 200..299
}

val otpRepo: OtpRepository = { creds, code ->
    val response = httpClient.post("/verify-otp") {
        setBody(mapOf("email" to creds.email, "otp" to code.code))
    }
    response.status.value in 200..299
}