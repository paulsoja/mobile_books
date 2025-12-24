package com.spasinnya.mentoring.data.net

import com.spasinnya.mentoring.data.mapper.mapToAppError
import com.spasinnya.mentoring.data.mapper.toDomainError
import com.spasinnya.mentoring.domain.rules.DomainResult
import com.spasinnya.mentoring.presentation.base.Validated
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.expectSuccess
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.HttpMethod
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

inline fun <reified T> HttpClient.requestFlow(
    method: HttpMethod,
    path: String,
    bodyObj: Any? = null,
    noinline builder: HttpRequestBuilder.() -> Unit = {}
): Flow<T> = flow {
    val response = request {
        url(path)
        this.method = method
        if (bodyObj != null) setBody(bodyObj)
        expectSuccess = true
        builder()
    }
    emit(response.body<T>())
}.catch { t ->
    throw mapToAppError(t)
}

inline fun <reified T> HttpClient.requestDomainFlow(
    method: HttpMethod,
    path: String,
    bodyObj: Any? = null,
    noinline builder: HttpRequestBuilder.() -> Unit = {}
): Flow<DomainResult<T>> = flow {
    val result: DomainResult<T> = try {
        val response = request {
            url(path)
            this.method = method
            if (bodyObj != null) setBody(bodyObj)
            expectSuccess = true
            builder()
        }
        val body = response.body<T>()
        Validated.Valid(body)
    } catch (t: Throwable) {
        Validated.Invalid(listOf(t.toDomainError()))
    }

    emit(result)
}

inline fun <Req : Any, reified Res> HttpClient.postFlow(
    path: String,
    body: Req? = null,
    noinline builder: HttpRequestBuilder.() -> Unit = {}
): Flow<DomainResult<Res>> = requestDomainFlow(
    method = HttpMethod.Post,
    path = path,
    bodyObj = body,
    builder = builder
)

inline fun <reified Res> HttpClient.getFlow(
    path: String,
    noinline builder: HttpRequestBuilder.() -> Unit = {}
): Flow<DomainResult<Res>> = requestDomainFlow(
    method = HttpMethod.Get,
    path = path,
    bodyObj = null,
    builder = builder
)