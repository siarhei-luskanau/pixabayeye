package siarhei.luskanau.pixabayeye.core.network.ktor

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import org.koin.core.annotation.Single
import sp.bvantur.inspektify.ktor.InspektifyKtor
import sp.bvantur.inspektify.ktor.LogLevel

@Single
internal class PlatformHttpClientEngineFactoryJvm : PlatformHttpClientEngineFactory {
    override fun <T : HttpClientEngineConfig> configureInspektify(block: HttpClientConfig<T>) {
        block.install(InspektifyKtor) {
            logLevel = LogLevel.All
        }
    }
}
