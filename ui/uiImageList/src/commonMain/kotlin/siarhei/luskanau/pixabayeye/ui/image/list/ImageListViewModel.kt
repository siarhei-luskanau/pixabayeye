package siarhei.luskanau.pixabayeye.ui.image.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.ahmad_hamwi.compose.pagination.PaginationState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam
import org.koin.core.annotation.Provided
import siarhei.luskanau.pixabayeye.core.network.HitModel
import siarhei.luskanau.pixabayeye.core.network.NetworkResult
import siarhei.luskanau.pixabayeye.core.network.PixabayApiService
import siarhei.luskanau.pixabayeye.ui.image.list.ImageListNavigationCallback

@Factory
class ImageListViewModel(
    @InjectedParam private val searchNavigationCallback: ImageListNavigationCallback,
    @Provided private val pixabayApiService: PixabayApiService
) : ViewModel() {

    private val searchTermFlow by lazy { MutableStateFlow("") }

    val paginationState: PaginationState<Int, HitModel> = PaginationState(
        initialPageKey = 1,
        onRequestPage = { pageKey ->
            loadPage(searchTerm = searchTermFlow.value, pageKey = pageKey)
        }
    )

    fun onEvent(event: ImageListViewEvent) {
        when (event) {
            ImageListViewEvent.DebugScreenClicked -> searchNavigationCallback.onDebugScreenClicked()
            is ImageListViewEvent.ImageClicked ->
                searchNavigationCallback.onSearchScreenImageClicked(
                    imageId = event.hitModel.imageId
                )
            is ImageListViewEvent.UpdateSearchTerm -> viewModelScope.launch {
                searchTermFlow.emit(event.searchTerm)
                paginationState.refresh()
            }
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
