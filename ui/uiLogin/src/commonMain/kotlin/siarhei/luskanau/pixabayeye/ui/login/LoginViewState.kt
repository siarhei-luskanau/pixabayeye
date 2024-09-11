package siarhei.luskanau.pixabayeye.ui.login

import kotlinx.coroutines.flow.Flow

data class LoginViewState(val apiKeyFlow: Flow<String?>)
