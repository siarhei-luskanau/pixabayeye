package siarhei.luskanau.pixabayeye.ui.search

import androidx.lifecycle.ViewModel
import app.cash.paging.PagingData
import kotlinx.coroutines.flow.Flow
import siarhei.luskanau.pixabayeye.core.network.HitModel

abstract class SearchViewModel : ViewModel() {
    abstract fun getPagingFlow(): Flow<PagingData<HitModel>>
    abstract fun onUpdateSearchTerm(searchTerm: String)
    abstract fun onImageClicked(hitModel: HitModel)
}
