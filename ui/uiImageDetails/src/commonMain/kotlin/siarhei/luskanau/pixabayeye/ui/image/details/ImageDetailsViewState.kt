package siarhei.luskanau.pixabayeye.ui.image.details

import siarhei.luskanau.pixabayeye.core.network.HitModel

sealed interface ImageDetailsViewState {
    data object Loading : ImageDetailsViewState
    data class Success(val hitModel: HitModel) : ImageDetailsViewState
    data class Error(val error: Throwable) : ImageDetailsViewState
}
