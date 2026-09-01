package siarhei.luskanau.pixabayeye.ui.media.list

import siarhei.luskanau.pixabayeye.core.network.api.HitModel

sealed interface MediaListViewEvent {
    data class UpdateSearchTerm(val searchTerm: String) : MediaListViewEvent
    data class ItemClicked(val hitModel: HitModel) : MediaListViewEvent
    data class TagClicked(val tag: String) : MediaListViewEvent
    data object DebugScreenClicked : MediaListViewEvent
    data object NavigateBack : MediaListViewEvent
}
