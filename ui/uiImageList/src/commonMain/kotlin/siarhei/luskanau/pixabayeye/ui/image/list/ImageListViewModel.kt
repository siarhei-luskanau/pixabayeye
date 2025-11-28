package siarhei.luskanau.pixabayeye.ui.image.list

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

class ImageListViewModel(
    private val imageListNavigationCallback: ImageListNavigationCallback,
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

    private fun loadPage(searchTerm: String, pageKey: Int) {
        viewModelScope.launch {
            when (
                val result = pixabayApiService.getImages(
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
