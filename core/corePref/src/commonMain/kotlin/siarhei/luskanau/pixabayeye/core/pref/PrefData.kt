package siarhei.luskanau.pixabayeye.core.pref

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PrefData(
    @SerialName("pixabayApiKey") val pixabayApiKey: String?
)
