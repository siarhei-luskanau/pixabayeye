package siarhei.luskanau.pixabayeye.ui.image.details

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
class ImageDetailsViewModel(
    @Provided private val pixabayApiService: PixabayApiService,
    @InjectedParam private val imageId: Long,
    @InjectedParam private val detailsNavigationCallback: ImageDetailsNavigationCallback
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
