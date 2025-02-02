package siarhei.luskanau.pixabayeye.ui.search

import app.cash.paging.PagingSource
import app.cash.paging.PagingSourceLoadParams
import app.cash.paging.PagingSourceLoadResult
import app.cash.paging.PagingSourceLoadResultError
import app.cash.paging.PagingState
import app.cash.paging.createPagingSourceLoadResultPage
import siarhei.luskanau.pixabayeye.core.network.HitModel
import siarhei.luskanau.pixabayeye.core.network.NetworkResult
import siarhei.luskanau.pixabayeye.core.network.PixabayApiService

class PixabayPagingSource(
    private val pixabayApiService: PixabayApiService,
    private val searchTerm: String
) : PagingSource<Int, HitModel>() {
    override suspend fun load(
        params: PagingSourceLoadParams<Int>
    ): PagingSourceLoadResult<Int, HitModel> {
        val page = params.key ?: FIRST_PAGE_INDEX
        val networkResult =
            pixabayApiService.getImages(
                query = searchTerm,
                perPage = params.loadSize,
                page = page
            )

        return when (networkResult) {
            is NetworkResult.Success ->
                createPagingSourceLoadResultPage(
                    data = networkResult.result,
                    prevKey = (page - 1).takeIf { it >= FIRST_PAGE_INDEX },
                    nextKey = if (networkResult.result.isNotEmpty()) page + 1 else null
                ) as PagingSourceLoadResult<Int, HitModel>

            is NetworkResult.Failure ->
                PagingSourceLoadResultError<Int, HitModel>(
                    networkResult.error
                ) as PagingSourceLoadResult<Int, HitModel>
        }
    }

    override fun getRefreshKey(state: PagingState<Int, HitModel>): Int? = null

    companion object {
        const val FIRST_PAGE_INDEX = 1
    }
}
