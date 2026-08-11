package siarhei.luskanau.pixabayeye.core.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import siarhei.luskanau.pixabayeye.core.network.ktor.IS_DEBUG_SCREEN_ENABLED
import siarhei.luskanau.pixabayeye.core.network.ktor.configureHttpClientDebug

@Module
@ComponentScan(value = ["siarhei.luskanau.pixabayeye.core.network.ktor"])
class CoreNetworkModule {
    @Single
    fun httpClient(): HttpClient = HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = !IS_DEBUG_SCREEN_ENABLED
                    prettyPrint = true
                }
            )
        }
        configureHttpClientDebug()
    }
}
