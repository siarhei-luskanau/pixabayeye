package siarhei.luskanau.pixabayeye.core.network.ktor.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class HitsResponse(
    @SerialName("total") val total: Int,
    @SerialName("totalHits") val totalHits: Int,
    @SerialName("hits") val hits: List<HitResponse>
)
