package siarhei.luskanau.pixabayeye.network.ktor

import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.okhttp.OkHttp

internal actual class PlatformHttpClientEngineFactory {
    actual fun get(): HttpClientEngineFactory<HttpClientEngineConfig> = OkHttp
}
