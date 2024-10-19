package siarhei.luskanau.pixabayeye.core.network.ktor

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory

internal expect class PlatformHttpClientEngineFactory() {
    fun get(): HttpClientEngineFactory<HttpClientEngineConfig>
    fun <T : HttpClientEngineConfig> configureInspektify(block: HttpClientConfig<T>)
}
