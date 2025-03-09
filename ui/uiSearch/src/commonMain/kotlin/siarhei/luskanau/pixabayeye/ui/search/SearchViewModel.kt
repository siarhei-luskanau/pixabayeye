package siarhei.luskanau.pixabayeye.ui.search

import androidx.lifecycle.ViewModel
import io.github.ahmad_hamwi.compose.pagination.PaginationState
import siarhei.luskanau.pixabayeye.core.network.HitModel

abstract class SearchViewModel : ViewModel() {
    abstract val paginationState: PaginationState<Int, HitModel>
    abstract fun onUpdateSearchTerm(searchTerm: String)
    abstract fun onImageClicked(hitModel: HitModel)
}
