package com.spasinnya.mentoring.data.net

import com.spasinnya.mentoring.data.mapper.mapHttpError
import com.spasinnya.mentoring.data.mapper.parseApiErrorOrNull
import com.spasinnya.mentoring.data.mapper.toDataError
import com.spasinnya.mentoring.data.model.DataResult
import com.spasinnya.mentoring.presentation.base.Validated
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpMethod
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json

val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
    explicitNulls = false
}

inline fun <reified T> HttpClient.requestDataFlow(
    json: Json,
    method: HttpMethod,
    path: String,
    bodyObj: Any? = null,
    noinline builder: HttpRequestBuilder.() -> Unit = {}
): Flow<DataResult<T>> = flow {
    val result: DataResult<T> = try {
        val response = request {
            url(path)
            this.method = method
            if (bodyObj != null) {
                setBody(bodyObj)
            }
            builder()
        }

        if (response.status.isSuccess()) {
            Validated.Valid(response.body<T>())
        } else {

            val rawErrorBody = runCatching {
                val test = response.bodyAsText()
                Napier.d { "requestDataFlow: ${test}" }
                test
            }.getOrNull()
            val apiError = parseApiErrorOrNull(
                json = json,
                raw = rawErrorBody
            )

            Validated.Invalid(
                mapHttpError(
                    statusCode = response.status.value,
                    apiError = apiError
                )
            )
        }
    } catch (t: Throwable) {
        Validated.Invalid(t.toDataError())
    }

    emit(result)
}

inline fun <Req : Any, reified Res> HttpClient.postFlow(
    path: String,
    body: Req? = null,
    noinline builder: HttpRequestBuilder.() -> Unit = {}
): Flow<DataResult<Res>> = requestDataFlow(
    json = json,
    method = HttpMethod.Post,
    path = path,
    bodyObj = body,
    builder = builder
)

inline fun <reified Res> HttpClient.getFlow(
    path: String,
    noinline builder: HttpRequestBuilder.() -> Unit = {}
): Flow<DataResult<Res>> = requestDataFlow(
    json = json,
    method = HttpMethod.Get,
    path = path,
    bodyObj = null,
    builder = builder
)

inline fun <Req : Any, reified Res> HttpClient.patchFlow(
    path: String,
    body: Req? = null,
    noinline builder: HttpRequestBuilder.() -> Unit = {}
): Flow<DataResult<Res>> = requestDataFlow(
    json = json,
    method = HttpMethod.Patch,
    path = path,
    bodyObj = body,
    builder = builder
)