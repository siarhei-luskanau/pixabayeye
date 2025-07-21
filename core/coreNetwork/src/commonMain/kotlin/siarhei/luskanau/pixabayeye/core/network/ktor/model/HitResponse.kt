package siarhei.luskanau.pixabayeye.core.network.ktor.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class HitResponse(
    @SerialName("id") val id: Long,
    @SerialName("pageURL") val pageURL: String,
    @SerialName("type") val type: String,
    @SerialName("tags") val tags: String,
    @SerialName("views") val views: Int,
    @SerialName("downloads") val downloads: Int,
    @SerialName("likes") val likes: Int,
    @SerialName("comments") val comments: Int,
    @SerialName("user_id") val userId: Long,
    @SerialName("user") val userName: String,
    @SerialName("userImageURL") val userImageURL: String,
    @SerialName("noAiTraining") val noAiTraining: Boolean,
    @SerialName("isAiGenerated") val isAiGenerated: Boolean,
    @SerialName("isGRated") val isGRated: Boolean,
    // @SerialName("isLowQuality") val isLowQuality: Boolean,
    @SerialName("userURL") val userURL: String,
    @SerialName("previewURL") val previewUrl: String? = null,
    @SerialName("previewWidth") val previewWidth: Int? = null,
    @SerialName("previewHeight") val previewHeight: Int? = null,
    @SerialName("webformatURL") val middleImageUrl: String? = null,
    @SerialName("webformatWidth") val middleImageWidth: Int? = null,
    @SerialName("webformatHeight") val middleImageHeight: Int? = null,
    @SerialName("largeImageURL") val largeImageUrl: String? = null,
    @SerialName("imageWidth") val imageWidth: Int? = null,
    @SerialName("imageHeight") val imageHeight: Int? = null,
    @SerialName("imageSize") val imageSize: Int? = null,
    @SerialName("collections") val collections: Int? = null,
    @SerialName("videos") val videos: Map<String, VideoHitResponse>? = null
)

@Serializable
internal data class VideoHitResponse(
    @SerialName("url") val url: String,
    @SerialName("width") val width: Int,
    @SerialName("height") val height: Int,
    @SerialName("size") val size: Int,
    @SerialName("thumbnail") val thumbnail: String
)
