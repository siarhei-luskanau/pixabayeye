package siarhei.luskanau.pixabayeye.ui.image.list

import siarhei.luskanau.pixabayeye.core.network.api.HitModel

sealed interface ImageListViewEvent {
    data class UpdateSearchTerm(val searchTerm: String) : ImageListViewEvent
    data class ImageClicked(val hitModel: HitModel) : ImageListViewEvent
    data class TagClicked(val tag: String) : ImageListViewEvent
    data object DebugScreenClicked : ImageListViewEvent
    data object NavigateBack : ImageListViewEvent
}
