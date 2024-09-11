package siarhei.luskanau.pixabayeye.ui.details

import siarhei.luskanau.pixabayeye.core.network.HitModel

sealed interface DetailsViewState {
    data object Loading : DetailsViewState
    data class Success(val hitModel: HitModel) : DetailsViewState
    data class Error(val error: Throwable) : DetailsViewState
}
