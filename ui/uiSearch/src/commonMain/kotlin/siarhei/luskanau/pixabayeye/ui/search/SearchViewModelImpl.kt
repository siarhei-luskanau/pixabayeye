package siarhei.luskanau.pixabayeye.ui.search

import androidx.lifecycle.viewModelScope
import app.cash.paging.PagingData
import app.cash.paging.createPager
import app.cash.paging.createPagingConfig
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam
import org.koin.core.annotation.Provided
import siarhei.luskanau.pixabayeye.core.network.HitModel
import siarhei.luskanau.pixabayeye.core.network.PixabayApiService

@Factory
class SearchViewModelImpl(
    @Provided private val pixabayApiService: PixabayApiService,
    @InjectedParam private val searchNavigationCallback: SearchNavigationCallback
) : SearchViewModel() {

    private val searchTermFlow by lazy { MutableStateFlow("") }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getPagingFlow(): Flow<PagingData<HitModel>> =
        searchTermFlow.flatMapLatest { searchTerm -> getPager(searchTerm).flow }

    override fun onUpdateSearchTerm(searchTerm: String) {
        viewModelScope.launch {
            searchTermFlow.emit(searchTerm)
        }
    }

    override fun onImageClicked(hitModel: HitModel) {
        searchNavigationCallback.onSearchScreenImageClicked(imageId = hitModel.imageId)
    }

    override fun onHomeClick() {
        searchNavigationCallback.onSearchScreenHomeClick()
    }

    override fun onLoginClick() {
        searchNavigationCallback.onSearchScreenLoginClick()
    }

    private fun getPager(searchTerm: String) = createPager(
        config = createPagingConfig(pageSize = 20, initialLoadSize = 20)
    ) {
        PixabayPagingSource(pixabayApiService, searchTerm)
    }
}
