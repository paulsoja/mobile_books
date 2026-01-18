package com.spasinnya.mentoring.data.net

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.darwin.Darwin

actual fun platformEngine(): HttpClientEngineFactory<*> = Darwin