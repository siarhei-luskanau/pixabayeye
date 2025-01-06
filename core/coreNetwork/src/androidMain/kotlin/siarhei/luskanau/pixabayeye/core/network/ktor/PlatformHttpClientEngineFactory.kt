package siarhei.luskanau.pixabayeye.core.network.ktor

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import sp.bvantur.inspektify.ktor.InspektifyKtor
import sp.bvantur.inspektify.ktor.LogLevel

internal actual class PlatformHttpClientEngineFactory {
    actual fun <T : HttpClientEngineConfig> configureInspektify(block: HttpClientConfig<T>) {
        block.install(InspektifyKtor) {
            logLevel = LogLevel.All
        }
    }
}
