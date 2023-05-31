package siarhei.luskanau.pixabayeye.ui.search

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import siarhei.luskanau.pixabayeye.network.HitModel
import siarhei.luskanau.pixabayeye.network.PixabayApiService

class SearchVewModel(private val pixabayApiService: PixabayApiService) {

    private val searchTermFlow by lazy { MutableStateFlow("") }

    fun getSearchVewStateFlow(): Flow<SearchVewState> =
        searchTermFlow.map {
            SearchVewState(
                searchTerm = it,
                pager = getPager(it),
            )
        }

    suspend fun onUpdateSearchTerm(searchTerm: String) {
        searchTermFlow.emit(searchTerm)
    }

    private fun getPager(searchTerm: String): Pager<Int, HitModel> {
        val pagingConfig = PagingConfig(pageSize = 20, initialLoadSize = 20)
        return Pager(pagingConfig) {
            PixabayPagingSource(pixabayApiService, searchTerm)
        }
    }
}
