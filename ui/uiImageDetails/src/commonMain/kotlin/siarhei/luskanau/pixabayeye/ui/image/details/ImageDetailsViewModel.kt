package siarhei.luskanau.pixabayeye.ui.image.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import siarhei.luskanau.pixabayeye.core.network.api.NetworkResult
import siarhei.luskanau.pixabayeye.core.network.api.PixabayApiService

class ImageDetailsViewModel(
    private val imageId: Long,
    private val detailsNavigationCallback: ImageDetailsNavigationCallback,
    private val pixabayApiService: PixabayApiService
) : ViewModel() {

    val viewState = MutableStateFlow<ImageDetailsViewState>(ImageDetailsViewState.Loading)

    fun onEvent(event: ImageDetailsViewEvent) {
        when (event) {
            ImageDetailsViewEvent.Launched -> viewModelScope.launch {
                when (val result = pixabayApiService.getImage(imageId = imageId)) {
                    is NetworkResult.Failure -> viewState.emit(
                        ImageDetailsViewState.Error(result.error)
                    )

                    is NetworkResult.Success -> viewState.emit(
                        ImageDetailsViewState.Success(result.result)
                    )
                }
            }

            ImageDetailsViewEvent.NavigateBack -> detailsNavigationCallback.goBack()
        }
    }
}
