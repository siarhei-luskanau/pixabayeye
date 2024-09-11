package siarhei.luskanau.pixabayeye.core.network.ktor

import siarhei.luskanau.pixabayeye.core.network.HitModel
import siarhei.luskanau.pixabayeye.core.network.NetworkResult
import siarhei.luskanau.pixabayeye.core.network.PixabayApiService
import siarhei.luskanau.pixabayeye.core.network.ktor.model.HitResponse

internal class KtorPixabayApiService(private val client: PixabayApiClient) : PixabayApiService {
    override suspend fun isApiKeyOk(apiKey: String?): NetworkResult<Boolean> = runNetworkCatching {
        client.isApiKeyOk(apiKey = apiKey)
    }

    override suspend fun getImages(
        query: String?,
        perPage: Int?,
        page: Int?
    ): NetworkResult<List<HitModel>> = runNetworkCatching {
        client.getImages(
            query = query,
            perPage = perPage,
            page = page
        ).let { imagesResponse ->
            imagesResponse.hits.map { it.toHitModel() }
        }
    }

    override suspend fun getImage(imageId: Long): NetworkResult<HitModel> = runNetworkCatching {
        client.getImage(
            imageId = imageId
        )
            .hits
            .first()
            .toHitModel()
    }

    private suspend fun <T> runNetworkCatching(action: suspend () -> T): NetworkResult<T> =
        runCatching {
            action.invoke()
        }.fold(
            onSuccess = { result: T -> NetworkResult.Success(result) },
            onFailure = { error: Throwable -> NetworkResult.Failure(error) }
        )
}

private fun HitResponse.toHitModel(): HitModel = HitModel(
    imageId = this.imageId,
    userId = this.userId,
    userName = this.userName,
    tags = this.tags,
    likes = this.likes,
    downloads = this.downloads,
    comments = this.comments,
    previewUrl = this.previewUrl,
    previewHeight = this.previewHeight,
    previewWidth = this.previewWidth,
    middleImageUrl = this.middleImageUrl,
    middleImageHeight = this.middleImageHeight,
    middleImageWidth = this.middleImageWidth,
    largeImageUrl = this.largeImageUrl
)
