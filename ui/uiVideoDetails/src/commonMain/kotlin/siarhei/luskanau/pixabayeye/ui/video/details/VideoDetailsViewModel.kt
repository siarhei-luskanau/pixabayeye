package siarhei.luskanau.pixabayeye.ui.video.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam
import org.koin.core.annotation.Provided
import siarhei.luskanau.pixabayeye.core.network.api.NetworkResult
import siarhei.luskanau.pixabayeye.core.network.api.PixabayApiService

@Factory
class VideoDetailsViewModel(
    @Provided private val pixabayApiService: PixabayApiService,
    @InjectedParam private val videoId: Long,
    @InjectedParam private val videoDetailsNavigationCallback: VideoDetailsNavigationCallback
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
