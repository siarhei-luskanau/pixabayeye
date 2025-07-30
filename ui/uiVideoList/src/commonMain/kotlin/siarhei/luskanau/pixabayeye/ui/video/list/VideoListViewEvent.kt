package siarhei.luskanau.pixabayeye.ui.video.list

import siarhei.luskanau.pixabayeye.core.network.HitModel

sealed interface VideoListViewEvent {
    data class UpdateSearchTerm(val searchTerm: String) : VideoListViewEvent
    data class VideoClicked(val hitModel: HitModel) : VideoListViewEvent
    data class TagClicked(val tag: String) : VideoListViewEvent
    data object DebugScreenClicked : VideoListViewEvent
    data object NavigateBack : VideoListViewEvent
}
