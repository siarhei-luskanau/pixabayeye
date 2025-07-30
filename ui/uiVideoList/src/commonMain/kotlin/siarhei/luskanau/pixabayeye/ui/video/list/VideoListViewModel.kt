package siarhei.luskanau.pixabayeye.ui.video.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.ahmad_hamwi.compose.pagination.PaginationState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam
import org.koin.core.annotation.Provided
import siarhei.luskanau.pixabayeye.core.network.HitModel
import siarhei.luskanau.pixabayeye.core.network.NetworkResult
import siarhei.luskanau.pixabayeye.core.network.PixabayApiService

@Factory
class VideoListViewModel(
    @InjectedParam private val videoListNavigationCallback: VideoListNavigationCallback,
    @InjectedParam private val initialSearchTerm: String?,
    @Provided private val pixabayApiService: PixabayApiService
) : ViewModel() {

    private val _searchTermFlow = MutableStateFlow(initialSearchTerm.orEmpty())
    val searchTermFlow: Flow<String> get() = _searchTermFlow

    val paginationState: PaginationState<Int, HitModel> = PaginationState(
        initialPageKey = 1,
        onRequestPage = { pageKey ->
            loadPage(searchTerm = _searchTermFlow.value, pageKey = pageKey)
        }
    )

    fun onEvent(event: VideoListViewEvent) {
        when (event) {
            VideoListViewEvent.DebugScreenClicked ->
                videoListNavigationCallback.onDebugScreenClicked()
            is VideoListViewEvent.VideoClicked ->
                videoListNavigationCallback
                    .onVideoListScreenVideoClicked(videoId = event.hitModel.id)
            is VideoListViewEvent.TagClicked -> viewModelScope.launch {
                _searchTermFlow.emit(event.tag)
                paginationState.refresh()
            }
            is VideoListViewEvent.UpdateSearchTerm -> viewModelScope.launch {
                _searchTermFlow.emit(event.searchTerm)
                paginationState.refresh()
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
