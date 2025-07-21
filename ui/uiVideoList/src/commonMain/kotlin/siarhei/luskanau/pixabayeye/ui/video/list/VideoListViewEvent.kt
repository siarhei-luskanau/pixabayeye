package siarhei.luskanau.pixabayeye.ui.video.list

import siarhei.luskanau.pixabayeye.core.network.HitModel

sealed interface VideoListViewEvent {
    data object DebugScreenClicked : VideoListViewEvent
    data class VideoClicked(val hitModel: HitModel) : VideoListViewEvent
    data class UpdateSearchTerm(val searchTerm: String) : VideoListViewEvent
}
