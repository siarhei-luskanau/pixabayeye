package siarhei.luskanau.pixabayeye.ui.app

import siarhei.luskanau.pixabayeye.network.PixabayApiService

internal class AppViewModelImpl(
    private val pixabayApiService: PixabayApiService,
) : AppViewModel {
    override suspend fun onClick() {
        pixabayApiService.isApiKeyOk()
    }
}
