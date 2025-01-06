package siarhei.luskanau.pixabayeye.core.network.ktor

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig

internal expect class PlatformHttpClientEngineFactory() {
    fun <T : HttpClientEngineConfig> configureInspektify(block: HttpClientConfig<T>)
}
