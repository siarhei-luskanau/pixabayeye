package siarhei.luskanau.pixabayeye.core.network.ktor

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig

fun <T : HttpClientEngineConfig> HttpClientConfig<T>.configureHttpClientDebug() {
    // no logs and debug in production
}
