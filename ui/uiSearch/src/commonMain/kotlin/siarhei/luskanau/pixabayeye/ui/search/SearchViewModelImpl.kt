package siarhei.luskanau.pixabayeye.ui.search

import androidx.lifecycle.viewModelScope
import io.github.ahmad_hamwi.compose.pagination.PaginationState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam
import org.koin.core.annotation.Provided
import siarhei.luskanau.pixabayeye.core.common.DispatcherSet
import siarhei.luskanau.pixabayeye.core.network.HitModel
import siarhei.luskanau.pixabayeye.core.network.NetworkResult
import siarhei.luskanau.pixabayeye.core.network.PixabayApiService

@Factory
class SearchViewModelImpl(
    @InjectedParam private val searchNavigationCallback: SearchNavigationCallback,
    @Provided private val pixabayApiService: PixabayApiService,
    @Provided private val dispatcherSet: DispatcherSet
) : SearchViewModel() {

    private val searchTermFlow by lazy { MutableStateFlow("") }

    override val paginationState: PaginationState<Int, HitModel> = PaginationState(
        initialPageKey = 1,
        onRequestPage = { pageKey ->
            loadPage(searchTerm = searchTermFlow.value, pageKey = pageKey)
        }
    )

    override fun onUpdateSearchTerm(searchTerm: String) {
        viewModelScope.launch {
            searchTermFlow.emit(searchTerm)
            paginationState.refresh()
        }
    }

    override fun onImageClicked(hitModel: HitModel) {
        searchNavigationCallback.onSearchScreenImageClicked(imageId = hitModel.imageId)
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
