package siarhei.luskanau.pixabayeye.core.network.ktor

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.serialization.json.Json
import siarhei.luskanau.pixabayeye.core.network.ktor.model.HitsResponse
import siarhei.luskanau.pixabayeye.core.pref.PrefService

internal class PixabayApiClient(private val prefService: PrefService) {

    private val httpClient: HttpClient by lazy {
        HttpClient {
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

    internal suspend fun getImages(query: String? = null, perPage: Int?, page: Int?): HitsResponse =
        geyPixabayApiKey().let { pixabayApiKey ->
            httpClient.get(PIXABAY_BASE_URL + "api/") {
                url {
                    parameters.append("key", pixabayApiKey)
                    query?.also { parameters.append("q", it) }
                    perPage?.also { parameters.append("per_page", it.toString()) }
                    page?.also { parameters.append("page", it.toString()) }
                }
            }.body()
        }

    internal suspend fun getImage(imageId: Long): HitsResponse =
        geyPixabayApiKey().let { pixabayApiKey ->
            httpClient.get(PIXABAY_BASE_URL + "api/") {
                url {
                    parameters.append("key", pixabayApiKey)
                    parameters.append("id", imageId.toString())
                }
            }.body()
        }

    internal suspend fun getVideos(query: String? = null, perPage: Int?, page: Int?): HitsResponse =
        geyPixabayApiKey().let { pixabayApiKey ->
            httpClient.get(PIXABAY_BASE_URL + "api/videos/") {
                url {
                    parameters.append("key", pixabayApiKey)
                    query?.also { parameters.append("q", it) }
                    perPage?.also { parameters.append("per_page", it.toString()) }
                    page?.also { parameters.append("page", it.toString()) }
                }
            }.body()
        }

    internal suspend fun getVideo(videoId: Long): HitsResponse =
        geyPixabayApiKey().let { pixabayApiKey ->
            httpClient.get(PIXABAY_BASE_URL + "api/videos/") {
                url {
                    parameters.append("key", pixabayApiKey)
                    parameters.append("id", videoId.toString())
                }
            }.body()
        }

    private suspend fun geyPixabayApiKey(): String =
        prefService.getPixabayApiKey().firstOrNull().orEmpty()

    companion object {
        private const val PIXABAY_BASE_URL = "https://pixabay.com/"
    }
}
