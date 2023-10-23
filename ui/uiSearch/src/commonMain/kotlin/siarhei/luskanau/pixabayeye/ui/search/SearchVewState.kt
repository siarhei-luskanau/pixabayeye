package siarhei.luskanau.pixabayeye.ui.search

import app.cash.paging.PagingData
import kotlinx.coroutines.flow.Flow
import siarhei.luskanau.pixabayeye.core.network.HitModel

data class SearchVewState(
    val pagingDataFlow: Flow<PagingData<HitModel>>,
)
