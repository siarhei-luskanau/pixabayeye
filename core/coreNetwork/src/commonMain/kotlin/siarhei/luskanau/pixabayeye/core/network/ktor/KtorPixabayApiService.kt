package siarhei.luskanau.pixabayeye.core.network.ktor

import org.koin.core.annotation.Single
import siarhei.luskanau.pixabayeye.core.network.HitModel
import siarhei.luskanau.pixabayeye.core.network.ImageHitModel
import siarhei.luskanau.pixabayeye.core.network.NetworkResult
import siarhei.luskanau.pixabayeye.core.network.PixabayApiService
import siarhei.luskanau.pixabayeye.core.network.VideoHitModel
import siarhei.luskanau.pixabayeye.core.network.ktor.model.HitResponse

@Single
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
            imagesResponse.hits.map { it.toImageHitModel() }
        }
    }

    override suspend fun getImage(imageId: Long): NetworkResult<HitModel> = runNetworkCatching {
        client.getImage(
            imageId = imageId
        )
            .hits
            .first()
            .toImageHitModel()
    }

    override suspend fun getVideos(
        query: String?,
        perPage: Int?,
        page: Int?
    ): NetworkResult<List<HitModel>> = runNetworkCatching {
        client.getVideos(
            query = query,
            perPage = perPage,
            page = page
        ).let { videosResponse ->
            videosResponse.hits.map { it.toVideoHitModel() }
        }
    }

    override suspend fun getVideo(videoId: Long): NetworkResult<HitModel> = runNetworkCatching {
        client.getVideo(
            videoId = videoId
        )
            .hits
            .first()
            .toVideoHitModel()
    }

    private suspend fun <T> runNetworkCatching(action: suspend () -> T): NetworkResult<T> =
        runCatching {
            action.invoke()
        }.fold(
            onSuccess = { result: T -> NetworkResult.Success(result) },
            onFailure = { error: Throwable -> NetworkResult.Failure(error) }
        )
}

private fun HitResponse.toImageHitModel(): HitModel = HitModel(
    id = this.id,
    pageURL = this.pageURL,
    type = this.type,
    tags = this.tags,
    views = this.views,
    downloads = this.downloads,
    likes = this.likes,
    comments = this.comments,
    userId = this.userId,
    userName = this.userName,
    userImageURL = this.userImageURL,
    noAiTraining = this.noAiTraining,
    isAiGenerated = this.isAiGenerated,
    isGRated = this.isGRated,
    // isLowQuality = this.isLowQuality,
    userURL = this.userURL,
    imageModel = ImageHitModel(
        previewUrl = requireNotNull(this.previewUrl),
        previewWidth = requireNotNull(this.previewWidth),
        previewHeight = requireNotNull(this.previewHeight),
        middleImageUrl = requireNotNull(this.middleImageUrl),
        middleImageWidth = requireNotNull(this.middleImageWidth),
        middleImageHeight = requireNotNull(this.middleImageHeight),
        largeImageUrl = requireNotNull(this.largeImageUrl),
        imageWidth = requireNotNull(this.imageWidth),
        imageHeight = requireNotNull(this.imageHeight),
        imageSize = requireNotNull(this.imageSize),
        collections = requireNotNull(this.collections)
    ),
    videosModel = null
)
private fun HitResponse.toVideoHitModel(): HitModel = HitModel(
    id = this.id,
    pageURL = this.pageURL,
    type = this.type,
    tags = this.tags,
    views = this.views,
    downloads = this.downloads,
    likes = this.likes,
    comments = this.comments,
    userId = this.userId,
    userName = this.userName,
    userImageURL = this.userImageURL,
    noAiTraining = this.noAiTraining,
    isAiGenerated = this.isAiGenerated,
    isGRated = this.isGRated,
    // isLowQuality = this.isLowQuality,
    userURL = this.userURL,
    imageModel = null,
    videosModel = this.videos.orEmpty().mapValues { (key, value) ->
        VideoHitModel(
            url = value.url,
            width = value.width,
            height = value.height,
            size = value.size,
            thumbnail = value.thumbnail
        )
    }
)
