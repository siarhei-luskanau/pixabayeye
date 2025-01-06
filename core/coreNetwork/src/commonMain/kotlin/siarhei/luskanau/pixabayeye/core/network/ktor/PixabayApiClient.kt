package siarhei.luskanau.pixabayeye.core.network.ktor

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single
import siarhei.luskanau.pixabayeye.core.common.DispatcherSet
import siarhei.luskanau.pixabayeye.core.network.ktor.model.ImagesResponse
import siarhei.luskanau.pixabayeye.core.pref.PrefService

@Single
internal class PixabayApiClient(
    @Provided private val prefService: PrefService,
    @Provided private val dispatcherSet: DispatcherSet
) {

    private val httpClient: HttpClient by lazy {
        HttpClient {
            PlatformHttpClientEngineFactory().configureInspektify(this)
            install(ContentNegotiation) {
                dispatcherSet.runBlocking(dispatcherSet.ioDispatcher()) {
                    json(
                        Json {
                            ignoreUnknownKeys = true
                            prettyPrint = true
                        }
                    )
                }
            }
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }
        }
    }

    internal suspend fun isApiKeyOk(apiKey: String?): Boolean =
        httpClient.get(PIXABAY_BASE_URL + "api/") {
            url {
                parameters.append("key", apiKey.orEmpty())
                parameters.append("per_page", "3")
                parameters.append("page", "1")
            }
        }.let { response ->
            if (response.status == HttpStatusCode.OK) {
                true
            } else {
                throw Error(response.status.toString())
            }
        }

    internal suspend fun getImages(
        query: String? = null,
        perPage: Int?,
        page: Int?
    ): ImagesResponse = geyPixabayApiKey().let { pixabayApiKey ->
        httpClient.get(PIXABAY_BASE_URL + "api/") {
            url {
                parameters.append("key", pixabayApiKey)
                query?.also { parameters.append("q", it) }
                perPage?.also { parameters.append("per_page", it.toString()) }
                page?.also { parameters.append("page", it.toString()) }
            }
        }.body()
    }

    internal suspend fun getImage(imageId: Long): ImagesResponse =
        geyPixabayApiKey().let { pixabayApiKey ->
            httpClient.get(PIXABAY_BASE_URL + "api/") {
                url {
                    parameters.append("key", pixabayApiKey)
                    parameters.append("id", imageId.toString())
                }
            }.body()
        }

    private suspend fun geyPixabayApiKey(): String =
        prefService.getPixabayApiKey().firstOrNull().orEmpty()

    companion object {
        private const val PIXABAY_BASE_URL = "https://pixabay.com/"
    }
}
