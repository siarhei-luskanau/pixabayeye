package siarhei.luskanau.pixabayeye.network.ktor.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class HitResponse(
    @SerialName("id") val imageId: Long,
    @SerialName("user_id") val userId: Long,
    @SerialName("user") val userName: String,
    @SerialName("tags") val tags: String,
    @SerialName("likes") val likes: Int,
    @SerialName("downloads") val downloads: Int,
    @SerialName("comments") val comments: Int,
    @SerialName("previewURL") val previewUrl: String,
    @SerialName("previewHeight") val previewHeight: Int,
    @SerialName("previewWidth") val previewWidth: Int,
    @SerialName("webformatURL") val middleImageUrl: String,
    @SerialName("largeImageURL") val largeImageUrl: String,
)
