package siarhei.luskanau.pixabayeye.ui.app

import siarhei.luskanau.pixabayeye.ui.login.LoginVewModel
import siarhei.luskanau.pixabayeye.ui.search.SearchVewModel
import siarhei.luskanau.pixabayeye.ui.splash.SplashVewModel

class AppViewModel(
    private val loginVewModel: (() -> Unit) -> LoginVewModel,
    val searchVewModel: SearchVewModel,
    val splashVewModel: SplashVewModel,
) {
    fun createLoginVewModel(onLoginComplete: () -> Unit): LoginVewModel =
        loginVewModel.invoke(onLoginComplete)
}
