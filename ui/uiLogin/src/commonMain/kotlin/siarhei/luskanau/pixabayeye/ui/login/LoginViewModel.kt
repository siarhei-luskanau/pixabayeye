package siarhei.luskanau.pixabayeye.ui.login

import androidx.lifecycle.ViewModel

abstract class LoginViewModel : ViewModel() {
    abstract fun getLoginViewState(): LoginViewState
    abstract fun onInit()
    abstract fun onUpdateClick(apiKey: String?)
    abstract fun onCheckClick()
}
