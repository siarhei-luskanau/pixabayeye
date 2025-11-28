package siarhei.luskanau.pixabayeye.ui.video.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.ahmad_hamwi.compose.pagination.PaginationState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import siarhei.luskanau.pixabayeye.core.network.api.HitModel
import siarhei.luskanau.pixabayeye.core.network.api.NetworkResult
import siarhei.luskanau.pixabayeye.core.network.api.PixabayApiService

class VideoListViewModel(
    private val videoListNavigationCallback: VideoListNavigationCallback,
    initialSearchTerm: String?,
    private val pixabayApiService: PixabayApiService
) : ViewModel() {

    private val _searchTermFlow = MutableStateFlow(initialSearchTerm.orEmpty())
    val searchTermFlow: Flow<String> get() = _searchTermFlow

    val paginationState: PaginationState<Int, HitModel> = PaginationState(
        initialPageKey = 1,
        onRequestPage = { pageKey ->
            loadPage(searchTerm = _searchTermFlow.value, pageKey = pageKey)
        }
    )

    init {
        _searchTermFlow
            .drop(1) // ignore initial value
            .debounce(500)
            .distinctUntilChanged()
            .onEach {
                paginationState.refresh()
            }
            .launchIn(viewModelScope)
    }

    fun onEvent(event: VideoListViewEvent) {
        when (event) {
            VideoListViewEvent.DebugScreenClicked ->
                videoListNavigationCallback.onDebugScreenClicked()

            is VideoListViewEvent.VideoClicked ->
                videoListNavigationCallback
                    .onVideoListScreenVideoClicked(videoId = event.hitModel.id)

            is VideoListViewEvent.TagClicked -> viewModelScope.launch {
                _searchTermFlow.emit(event.tag)
            }

            is VideoListViewEvent.UpdateSearchTerm -> viewModelScope.launch {
                _searchTermFlow.emit(event.searchTerm)
            }

            VideoListViewEvent.NavigateBack -> videoListNavigationCallback.goBack()
        }
    }

    private fun loadPage(searchTerm: String, pageKey: Int) {
        viewModelScope.launch {
            when (
                val result = pixabayApiService.getVideos(
                    query = searchTerm,
                    perPage = 20,
                    page = pageKey
                )
            ) {
                is NetworkResult.Failure -> paginationState.setError(result.error as Exception)

                is NetworkResult.Success -> paginationState.appendPage(
                    items = result.result,
                    nextPageKey = pageKey + 1,
                    isLastPage = result.result.size < 20
                )
            }
        }
    }
}
