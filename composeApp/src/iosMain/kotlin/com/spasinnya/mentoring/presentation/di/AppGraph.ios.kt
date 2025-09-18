package com.spasinnya.mentoring.presentation.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import co.touchlab.stately.freeze
import com.spasinnya.mentoring.data.net.createHttpClient
import com.spasinnya.mentoring.data.storage.datastore.provideTokenStore
import io.ktor.client.HttpClient
import kotlin.concurrent.AtomicReference

private object GraphHolder {
    val ref = AtomicReference<AppGraph?>(null)
}

actual fun provideAppGraph(appContext: Any): AppGraph {
    GraphHolder.ref.value?.let { return it }

    val tokenStore = provideTokenStore(Unit)
    val http: HttpClient = createHttpClient(tokenStore)
    val repos = provideRepoModule(http, tokenStore)
    val useCases = provideUseCaseModule(repos)

    val graph = AppGraph(tokenStore, http, repos, useCases)
    GraphHolder.ref.value = graph.freeze()
    return graph
}

actual fun resetAppGraph() {
    GraphHolder.ref.value?.let { old ->
        try { old.http.close() } catch (_: Throwable) { /* ignore */ }
    }
    GraphHolder.ref.value = null
}

actual inline fun <reified T : ViewModel> viewModelFactory(
    crossinline builder: ViewModelBuilder<T>
): ViewModelProvider.Factory = viewModelFactory {
    initializer {
        val handle = createSavedStateHandle()
        val graph: AppGraph = provideAppGraph(Unit)
        builder(graph, handle)
    }
}

