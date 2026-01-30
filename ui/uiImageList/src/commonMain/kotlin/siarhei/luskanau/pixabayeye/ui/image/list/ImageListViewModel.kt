package siarhei.luskanau.pixabayeye.ui.image.list

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
import siarhei.luskanau.pixabayeye.core.network.api.HitModel
import siarhei.luskanau.pixabayeye.core.network.api.NetworkResult
import siarhei.luskanau.pixabayeye.core.network.api.PixabayApiService

class ImageListViewModel(
    private val imageListNavigationCallback: ImageListNavigationCallback,
    initialSearchTerm: String?,
    private val pixabayApiService: PixabayApiService
) : ViewModel() {

    private val _searchTermFlow = MutableStateFlow(initialSearchTerm.orEmpty())
    val searchTermFlow: Flow<String> get() = _searchTermFlow

    private var currentPagingSource: ImagesPagingSource? = null

    val pagingDataFlow: Flow<PagingData<HitModel>> = Pager(
        config = PagingConfig(pageSize = 20)
    ) {
        ImagesPagingSource(pixabayApiService, _searchTermFlow.value).also {
            currentPagingSource = it
        }
    }.flow.cachedIn(viewModelScope)

    init {
        _searchTermFlow
            .drop(1) // ignore initial value
            .debounce(500)
            .distinctUntilChanged()
            .onEach {
                currentPagingSource?.invalidate()
            }
            .launchIn(viewModelScope)
    }

    fun onEvent(event: ImageListViewEvent) {
        when (event) {
            ImageListViewEvent.DebugScreenClicked ->
                imageListNavigationCallback.onDebugScreenClicked()

            is ImageListViewEvent.ImageClicked ->
                imageListNavigationCallback.onSearchScreenImageClicked(imageId = event.hitModel.id)

            is ImageListViewEvent.TagClicked -> viewModelScope.launch {
                _searchTermFlow.emit(event.tag)
                imageListNavigationCallback.onImageTagClicked(tag = event.tag)
            }

            is ImageListViewEvent.UpdateSearchTerm -> viewModelScope.launch {
                _searchTermFlow.emit(event.searchTerm)
            }

            ImageListViewEvent.NavigateBack -> imageListNavigationCallback.goBack()
        }
    }
}

private class ImagesPagingSource(
    private val pixabayApiService: PixabayApiService,
    private val query: String
) : PagingSource<Int, HitModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HitModel> {
        val page = params.key ?: 1
        return when (
            val result = pixabayApiService.getImages(
                query = query,
                perPage = 20,
                page = page
            )
        ) {
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
