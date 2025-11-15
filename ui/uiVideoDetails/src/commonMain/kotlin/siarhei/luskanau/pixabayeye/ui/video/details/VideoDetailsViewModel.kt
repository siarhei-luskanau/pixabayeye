package siarhei.luskanau.pixabayeye.ui.video.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import siarhei.luskanau.pixabayeye.core.network.api.NetworkResult
import siarhei.luskanau.pixabayeye.core.network.api.PixabayApiService

class VideoDetailsViewModel(
    private val videoId: Long,
    private val videoDetailsNavigationCallback: VideoDetailsNavigationCallback,
    private val pixabayApiService: PixabayApiService
) : ViewModel() {

    val viewState = MutableStateFlow<VideoDetailsViewState>(VideoDetailsViewState.Loading)

    fun onEvent(event: VideoDetailsViewEvent) {
        when (event) {
            VideoDetailsViewEvent.Launched -> viewModelScope.launch {
                when (val result = pixabayApiService.getVideo(videoId = videoId)) {
                    is NetworkResult.Failure -> viewState.emit(
                        VideoDetailsViewState.Error(result.error)
                    )

                    is NetworkResult.Success -> viewState.emit(
                        VideoDetailsViewState.Success(result.result)
                    )
                }
            }

            VideoDetailsViewEvent.NavigateBack -> videoDetailsNavigationCallback.goBack()
        }
    }
}
