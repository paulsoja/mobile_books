package com.spasinnya.mentoring.data.net

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.cio.CIO

actual fun platformEngine(): HttpClientEngineFactory<*> = CIO