package siarhei.luskanau.pixabayeye.network.ktor

import PixabayEye.network.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import siarhei.luskanau.pixabayeye.network.ktor.model.ImagesResponse

internal class PixabayApiClient {

    private val engine: HttpClientEngineFactory<HttpClientEngineConfig> by lazy {
        PlatformHttpClientEngineFactory().get()
    }

    private val httpClient: HttpClient by lazy {
        HttpClient(engine) {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                    },
                )
            }
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }
        }
    }

    internal suspend fun isApiKeyOk(): Boolean =
        httpClient.get(PIXABAY_BASE_URL + "api/") {
            url {
                parameters.append("key", BuildConfig.PIXABAY_API_KEY)
                parameters.append("per_page", "3")
                parameters.append("page", "1")
            }
        }.status == HttpStatusCode.OK

    internal suspend fun getImages(
        query: String? = null,
        perPage: Int?,
        page: Int?,
    ): ImagesResponse =
        httpClient.get(PIXABAY_BASE_URL + "api/") {
            url {
                parameters.append("key", BuildConfig.PIXABAY_API_KEY)
                query?.also { parameters.append("q", it) }
                perPage?.also { parameters.append("per_page", it.toString()) }
                page?.also { parameters.append("page", it.toString()) }
            }
        }.body()

    companion object {
        private const val PIXABAY_BASE_URL = "https://pixabay.com/"
    }
}
