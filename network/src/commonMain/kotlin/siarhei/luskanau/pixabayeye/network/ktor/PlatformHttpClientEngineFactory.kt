package siarhei.luskanau.pixabayeye.network.ktor

import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory

internal expect class PlatformHttpClientEngineFactory() {
    fun get(): HttpClientEngineFactory<HttpClientEngineConfig>
}
