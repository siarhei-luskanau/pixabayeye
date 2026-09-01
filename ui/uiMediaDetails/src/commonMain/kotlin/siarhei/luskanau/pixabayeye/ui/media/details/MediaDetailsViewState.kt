package siarhei.luskanau.pixabayeye.ui.media.details

import siarhei.luskanau.pixabayeye.core.network.api.HitModel

sealed interface MediaDetailsViewState {
    data object Loading : MediaDetailsViewState
    data class Success(val hitModel: HitModel, val isTest: Boolean = false) : MediaDetailsViewState
    data class Error(val error: Throwable) : MediaDetailsViewState
}
