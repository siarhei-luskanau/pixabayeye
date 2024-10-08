package siarhei.luskanau.pixabayeye.core.network

interface PixabayApiService {
    suspend fun isApiKeyOk(apiKey: String?): NetworkResult<Boolean>

    suspend fun getImages(query: String?, perPage: Int?, page: Int?): NetworkResult<List<HitModel>>

    suspend fun getImage(imageId: Long): NetworkResult<HitModel>
}

sealed interface NetworkResult<T> {
    data class Failure<T>(val error: Throwable) : NetworkResult<T>

    data class Success<T>(val result: T) : NetworkResult<T>
}

data class HitModel(
    val imageId: Long,
    val userId: Long,
    val userName: String,
    val tags: String,
    val likes: Int,
    val downloads: Int,
    val comments: Int,
    val previewUrl: String,
    val previewHeight: Int,
    val previewWidth: Int,
    val middleImageUrl: String,
    val middleImageHeight: Int,
    val middleImageWidth: Int,
    val largeImageUrl: String
)
