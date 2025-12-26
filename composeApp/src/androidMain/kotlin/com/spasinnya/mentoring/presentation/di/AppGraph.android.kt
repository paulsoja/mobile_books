package com.spasinnya.mentoring.presentation.di

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.spasinnya.mentoring.data.net.createHttpClient
import io.ktor.client.HttpClient
import java.util.concurrent.atomic.AtomicReference

private object GraphHolder {
    val ref = AtomicReference<AppGraph?>(null)
}

actual fun provideAppGraph(appContext: Any): AppGraph {
    GraphHolder.ref.get()?.let { return it }

    synchronized(GraphHolder) {
        GraphHolder.ref.get()?.let { return it }

        val ctx = (appContext as Context).applicationContext

        val dataSource = provideDataSourceModule(ctx)
        val http: HttpClient = createHttpClient(dataSource.tokenStore)
        val repos = provideRepoModule(http, dataSource)
        val useCases = provideUseCaseModule(repos)

        val graph = AppGraph(dataSource, http, repos, useCases)
        GraphHolder.ref.set(graph)
        return graph
    }
}

actual fun resetAppGraph() {
    synchronized(GraphHolder) {
        GraphHolder.ref.getAndSet(null)?.let { old ->
            try { old.http.close() } catch (_: Throwable) { /* ignore */ }
        }
    }
}

actual inline fun <reified T : ViewModel> viewModelFactory(
    crossinline builder: ViewModelBuilder<T>
): ViewModelProvider.Factory = viewModelFactory {
    initializer {
        val app = this[APPLICATION_KEY] as Application
        val handle = createSavedStateHandle()
        val graph = provideAppGraph(app.applicationContext)
        builder(graph, handle)
    }
}