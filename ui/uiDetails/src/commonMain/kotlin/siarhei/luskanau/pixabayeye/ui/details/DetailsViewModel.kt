package siarhei.luskanau.pixabayeye.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam
import org.koin.core.annotation.Provided
import siarhei.luskanau.pixabayeye.core.network.NetworkResult
import siarhei.luskanau.pixabayeye.core.network.PixabayApiService

@Factory
class DetailsViewModel(
    @Provided private val pixabayApiService: PixabayApiService,
    @InjectedParam private val imageId: Long,
    @InjectedParam private val detailsNavigationCallback: DetailsNavigationCallback
) : ViewModel() {

    val viewState = MutableStateFlow<DetailsViewState>(DetailsViewState.Loading)

    fun onEvent(event: DetailsViewEvent) {
        when (event) {
            DetailsViewEvent.Launched -> viewModelScope.launch {
                when (val result = pixabayApiService.getImage(imageId = imageId)) {
                    is NetworkResult.Failure -> viewState.emit(DetailsViewState.Error(result.error))
                    is NetworkResult.Success -> viewState.emit(
                        DetailsViewState.Success(result.result)
                    )
                }
            }
            DetailsViewEvent.NavigateBack -> detailsNavigationCallback.goBack()
        }
    }
}
