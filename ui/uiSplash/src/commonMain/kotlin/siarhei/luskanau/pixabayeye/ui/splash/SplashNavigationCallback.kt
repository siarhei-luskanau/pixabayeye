package siarhei.luskanau.pixabayeye.ui.splash

interface SplashNavigationCallback {
    fun onSplashScreenCompleted(loginState: LoginState)

    sealed interface LoginState {
        data object HasLogin : LoginState
        data object NoLogin : LoginState
    }
}
