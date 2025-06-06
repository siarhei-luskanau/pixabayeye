package siarhei.luskanau.pixabayeye.ui.search

import siarhei.luskanau.pixabayeye.core.network.HitModel

sealed interface SearchViewEvent {
    data class UpdateSearchTerm(val searchTerm: String) : SearchViewEvent
    data class ImageClicked(val hitModel: HitModel) : SearchViewEvent
    object DebugScreenClicked : SearchViewEvent
}
