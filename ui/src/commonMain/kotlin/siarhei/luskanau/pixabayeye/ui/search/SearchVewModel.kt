package siarhei.luskanau.pixabayeye.ui.search

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import siarhei.luskanau.pixabayeye.network.HitModel
import siarhei.luskanau.pixabayeye.network.PixabayApiService

class SearchVewModel(
    private val pixabayApiService: PixabayApiService,
) {
    fun getPager(latestSearchTerm: String): Pager<Int, HitModel> {
        val pagingConfig = PagingConfig(pageSize = 20, initialLoadSize = 20)
        return Pager(pagingConfig) {
            PixabayPagingSource(pixabayApiService, latestSearchTerm)
        }
    }
}
