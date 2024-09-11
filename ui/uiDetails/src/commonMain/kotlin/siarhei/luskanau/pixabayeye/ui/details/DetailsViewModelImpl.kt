package siarhei.luskanau.pixabayeye.ui.details

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam
import org.koin.core.annotation.Provided
import siarhei.luskanau.pixabayeye.core.network.NetworkResult
import siarhei.luskanau.pixabayeye.core.network.PixabayApiService

@Factory
class DetailsViewModelImpl(
    @Provided private val pixabayApiService: PixabayApiService,
    @InjectedParam private val imageId: Long,
    @InjectedParam private val detailsNavigationCallback: DetailsNavigationCallback
) : DetailsViewModel() {

    override val viewState = MutableStateFlow<DetailsViewState>(DetailsViewState.Loading)

    override fun onLaunched() {
        viewModelScope.launch {
            when (val result = pixabayApiService.getImage(imageId = imageId)) {
                is NetworkResult.Failure -> viewState.emit(DetailsViewState.Error(result.error))
                is NetworkResult.Success -> viewState.emit(DetailsViewState.Success(result.result))
            }
        }
    }

    override fun onBackClick() {
        detailsNavigationCallback.goBack()
    }
}
