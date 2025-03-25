package siarhei.luskanau.pixabayeye.core.network.ktor

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import org.koin.core.annotation.Single

@Single
internal class PlatformHttpClientEngineFactoryJs : PlatformHttpClientEngineFactory {
    override fun <T : HttpClientEngineConfig> configureInspektify(block: HttpClientConfig<T>) = Unit
}
