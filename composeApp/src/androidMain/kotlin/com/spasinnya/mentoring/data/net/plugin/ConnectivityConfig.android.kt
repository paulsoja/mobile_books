package com.spasinnya.mentoring.data.net.plugin

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

actual object NetStatus {
    private lateinit var appContext: Context
    fun init(context: Context) { appContext = context.applicationContext }
    actual fun isOnline(): Boolean = appContext.appIsOnline()
}

fun Context.appIsOnline(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = cm.activeNetwork ?: return false
    val caps = cm.getNetworkCapabilities(network) ?: return false
    return caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
            caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
}