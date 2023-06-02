package siarhei.luskanau.pixabayeye.network.ktor

import siarhei.luskanau.pixabayeye.network.HitModel
import siarhei.luskanau.pixabayeye.network.NetworkResult
import siarhei.luskanau.pixabayeye.network.PixabayApiService

internal class KtorPixabayApiService(
    private val client: PixabayApiClient,
) : PixabayApiService {

    override suspend fun isApiKeyOk(apiKey: String?): NetworkResult<Boolean> =
        runNetworkCatching {
            client.isApiKeyOk(apiKey = apiKey)
        }

    override suspend fun getImages(
        query: String?,
        perPage: Int?,
        page: Int?,
    ): NetworkResult<List<HitModel>> =
        runNetworkCatching {
            client.getImages(
                query = query,
                perPage = perPage,
                page = page,
            ).let { imagesResponse ->
                imagesResponse.hits.map { entity ->
                    HitModel(
                        imageId = entity.imageId,
                        userId = entity.userId,
                        userName = entity.userName,
                        tags = entity.tags,
                        likes = entity.likes,
                        downloads = entity.downloads,
                        comments = entity.comments,
                        previewUrl = entity.previewUrl,
                        previewHeight = entity.previewHeight,
                        previewWidth = entity.previewWidth,
                        middleImageUrl = entity.middleImageUrl,
                        largeImageUrl = entity.largeImageUrl,
                    )
                }
            }
        }

    private suspend fun <T> runNetworkCatching(
        action: suspend () -> T,
    ): NetworkResult<T> =
        runCatching {
            action.invoke()
        }.fold(
            onSuccess = { result: T -> NetworkResult.Success(result) },
            onFailure = { error: Throwable -> NetworkResult.Failure(error) },
        )
}
