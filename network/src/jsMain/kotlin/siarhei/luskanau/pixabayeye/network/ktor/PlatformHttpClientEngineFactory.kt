package siarhei.luskanau.pixabayeye.network.ktor

import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.js.Js

internal actual class PlatformHttpClientEngineFactory {
    actual fun get(): HttpClientEngineFactory<HttpClientEngineConfig> = Js
}
