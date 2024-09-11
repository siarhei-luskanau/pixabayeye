package siarhei.luskanau.pixabayeye.ui.details

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

abstract class DetailsViewModel : ViewModel() {
    abstract val viewState: StateFlow<DetailsViewState>
    abstract fun onLaunched()
    abstract fun onBackClick()
}
