package siarhei.luskanau.pixabayeye.ui.media.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.core.annotation.InjectedParam
import org.koin.core.annotation.KoinViewModel
import org.koin.core.annotation.Provided
import siarhei.luskanau.pixabayeye.core.common.MediaType
import siarhei.luskanau.pixabayeye.core.network.api.HitModel
import siarhei.luskanau.pixabayeye.core.network.api.NetworkResult
import siarhei.luskanau.pixabayeye.core.network.api.PixabayApiService

@KoinViewModel
class MediaListViewModel(
    @InjectedParam private val mediaType: MediaType,
    @InjectedParam private val mediaListNavigationCallback: MediaListNavigationCallback,
    @InjectedParam initialSearchTerm: String?,
    @Provided private val pixabayApiService: PixabayApiService
) : ViewModel() {

    val searchTermFlow: Flow<String>
        field = MutableStateFlow<String>(initialSearchTerm.orEmpty())

    private var currentPagingSource: MediaPagingSource? = null

    val pagingDataFlow: Flow<PagingData<HitModel>> = Pager(
        config = PagingConfig(pageSize = 20)
    ) {
        MediaPagingSource(mediaType, pixabayApiService, searchTermFlow.value).also {
            currentPagingSource = it
        }
    }.flow.cachedIn(viewModelScope)

    init {
        searchTermFlow
            .drop(1) // ignore initial value
            .debounce(500)
            .distinctUntilChanged()
            .onEach {
                currentPagingSource?.invalidate()
            }
            .launchIn(viewModelScope)

        searchTermFlow
            .onEach { mediaListNavigationCallback.onSearchTermChanged(searchTerm = it) }
            .launchIn(viewModelScope)
    }

    fun onEvent(event: MediaListViewEvent) {
        when (event) {
            MediaListViewEvent.DebugScreenClicked ->
                mediaListNavigationCallback.onDebugScreenClicked()

            is MediaListViewEvent.ItemClicked ->
                mediaListNavigationCallback.onMediaListScreenItemClicked(
                    id = event.hitModel.id,
                    mediaType = mediaType
                )

            is MediaListViewEvent.TagClicked -> viewModelScope.launch {
                searchTermFlow.emit(event.tag)
                if (mediaType == MediaType.IMAGE) {
                    mediaListNavigationCallback.onImageTagClicked(tag = event.tag)
                }
            }

            is MediaListViewEvent.UpdateSearchTerm -> viewModelScope.launch {
                searchTermFlow.emit(event.searchTerm)
            }

            MediaListViewEvent.NavigateBack -> mediaListNavigationCallback.goBack()
        }
    }
}

private class MediaPagingSource(
    private val mediaType: MediaType,
    private val pixabayApiService: PixabayApiService,
    private val query: String
) : PagingSource<Int, HitModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HitModel> {
        val page = params.key ?: 1
        val result = when (mediaType) {
            MediaType.IMAGE -> pixabayApiService.getImages(query = query, perPage = 20, page = page)
            MediaType.VIDEO -> pixabayApiService.getVideos(query = query, perPage = 20, page = page)
        }
        return when (result) {
            is NetworkResult.Failure -> LoadResult.Error(result.error as Exception)

            is NetworkResult.Success -> LoadResult.Page(
                data = result.result,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (result.result.size < 20) null else page + 1
            )
        }
    }

    override fun getRefreshKey(state: PagingState<Int, HitModel>): Int? =
        state.anchorPosition?.let { pos ->
            state.closestPageToPosition(pos)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(pos)?.nextKey?.minus(1)
        }
}
