package siarhei.luskanau.pixabayeye.ui.media.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.annotation.InjectedParam
import org.koin.core.annotation.KoinViewModel
import org.koin.core.annotation.Provided
import siarhei.luskanau.pixabayeye.core.common.MediaType
import siarhei.luskanau.pixabayeye.core.network.api.NetworkResult
import siarhei.luskanau.pixabayeye.core.network.api.PixabayApiService

@KoinViewModel
class MediaDetailsViewModel(
    @InjectedParam private val mediaType: MediaType,
    @InjectedParam private val id: Long,
    @InjectedParam private val mediaDetailsNavigationCallback: MediaDetailsNavigationCallback,
    @Provided private val pixabayApiService: PixabayApiService
) : ViewModel() {

    val viewState: StateFlow<MediaDetailsViewState>
        field = MutableStateFlow<MediaDetailsViewState>(MediaDetailsViewState.Loading)

    fun onEvent(event: MediaDetailsViewEvent) {
        when (event) {
            MediaDetailsViewEvent.Launched -> viewModelScope.launch {
                val result = when (mediaType) {
                    MediaType.IMAGE -> pixabayApiService.getImage(imageId = id)
                    MediaType.VIDEO -> pixabayApiService.getVideo(videoId = id)
                }
                when (result) {
                    is NetworkResult.Failure -> viewState.emit(
                        MediaDetailsViewState.Error(result.error)
                    )

                    is NetworkResult.Success -> viewState.emit(
                        MediaDetailsViewState.Success(hitModel = result.result)
                    )
                }
            }

            MediaDetailsViewEvent.NavigateBack -> mediaDetailsNavigationCallback.goBack()
        }
    }
}
