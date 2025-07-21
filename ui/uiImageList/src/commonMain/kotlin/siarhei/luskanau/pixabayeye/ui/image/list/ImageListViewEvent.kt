package siarhei.luskanau.pixabayeye.ui.image.list

import siarhei.luskanau.pixabayeye.core.network.HitModel

sealed interface ImageListViewEvent {
    data class UpdateSearchTerm(val searchTerm: String) : ImageListViewEvent
    data class ImageClicked(val hitModel: HitModel) : ImageListViewEvent
    object DebugScreenClicked : ImageListViewEvent
}
