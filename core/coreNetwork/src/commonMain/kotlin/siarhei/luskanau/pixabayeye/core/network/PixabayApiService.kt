package siarhei.luskanau.pixabayeye.core.network

interface PixabayApiService {
    suspend fun isApiKeyOk(apiKey: String?): NetworkResult<Boolean>

    suspend fun getImages(query: String?, perPage: Int?, page: Int?): NetworkResult<List<HitModel>>

    suspend fun getImage(imageId: Long): NetworkResult<HitModel>

    suspend fun getVideos(query: String?, perPage: Int?, page: Int?): NetworkResult<List<HitModel>>

    suspend fun getVideo(videoId: Long): NetworkResult<HitModel>
}

sealed interface NetworkResult<T> {
    data class Failure<T>(val error: Throwable) : NetworkResult<T>

    data class Success<T>(val result: T) : NetworkResult<T>
}

data class HitModel(
    val id: Long,
    val pageURL: String,
    val type: String,
    val tags: String,
    val views: Int,
    val downloads: Int,
    val likes: Int,
    val comments: Int,
    val userId: Long,
    val userName: String,
    val userImageURL: String,
    val noAiTraining: Boolean,
    val isAiGenerated: Boolean,
    val isGRated: Boolean,
    // val isLowQuality: Boolean,
    val userURL: String,
    val imageModel: ImageHitModel?,
    val videosModel: Map<String, VideoHitModel>?
)

data class ImageHitModel(
    val previewUrl: String,
    val previewWidth: Int,
    val previewHeight: Int,
    val middleImageUrl: String,
    val middleImageWidth: Int,
    val middleImageHeight: Int,
    val largeImageUrl: String,
    val imageWidth: Int,
    val imageHeight: Int,
    val imageSize: Int,
    val collections: Int
)

data class VideoHitModel(
    val url: String,
    val width: Int,
    val height: Int,
    val size: Int,
    val thumbnail: String
)
