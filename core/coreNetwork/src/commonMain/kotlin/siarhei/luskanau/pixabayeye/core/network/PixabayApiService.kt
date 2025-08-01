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

val testData = HitModel(
    id = 123,
    pageURL = "",
    type = "photo",
    tags = "tag1, tag2, tag3",
    views = 1,
    comments = 300,
    downloads = 200,
    likes = 100,
    userId = 456,
    userName = "John Doe",
    userImageURL = "",
    noAiTraining = false,
    isAiGenerated = true,
    isGRated = false,
    // isLowQuality = false,
    userURL = "John Doe",
    imageModel = ImageHitModel(
        previewUrl = "https://example.com/preview.jpg",
        previewHeight = 100,
        previewWidth = 100,
        middleImageUrl = "https://example.com/middle.jpg",
        middleImageHeight = 200,
        middleImageWidth = 200,
        largeImageUrl = "https://example.com/large.jpg",
        imageWidth = 600,
        imageHeight = 600,
        imageSize = 12345,
        collections = 0
    ),
    videosModel = mapOf(
        "large" to VideoHitModel(
            url = "https://example.com/video.mp4",
            width = 1304,
            height = 719,
            size = 6621423,
            thumbnail = "https://example.com/preview.jpg"
        )
    )
)
