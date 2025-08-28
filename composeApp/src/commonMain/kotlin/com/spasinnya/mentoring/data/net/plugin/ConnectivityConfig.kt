package com.spasinnya.mentoring.data.net.plugin

import io.ktor.client.plugins.api.Send
import io.ktor.client.plugins.api.createClientPlugin

typealias IsOnline = () -> Boolean

class NoInternetException : Exception("No internet connection")

class ConnectivityConfig internal constructor() {
    var isOnline: IsOnline = { true }
}

val Connectivity = createClientPlugin(
    name = "Connectivity",
    createConfiguration = ::ConnectivityConfig
) {
    val isOnlineFun = pluginConfig.isOnline

    on(Send) { request ->
        if (!isOnlineFun()) throw NoInternetException()
        proceed(request)
    }
}

expect object NetStatus {
    fun isOnline(): Boolean
}