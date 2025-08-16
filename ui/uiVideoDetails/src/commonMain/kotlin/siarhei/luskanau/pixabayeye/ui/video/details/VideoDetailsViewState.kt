package siarhei.luskanau.pixabayeye.ui.video.details

import siarhei.luskanau.pixabayeye.core.network.api.HitModel

sealed interface VideoDetailsViewState {
    data object Loading : VideoDetailsViewState
    data class Success(val hitModel: HitModel, val isTest: Boolean = false) : VideoDetailsViewState
    data class Error(val error: Throwable) : VideoDetailsViewState
}
