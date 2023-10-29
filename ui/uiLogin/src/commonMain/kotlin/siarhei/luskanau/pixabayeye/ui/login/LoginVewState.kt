package siarhei.luskanau.pixabayeye.ui.login

import kotlinx.coroutines.flow.Flow

data class LoginVewState(
    val apiKeyFlow: Flow<String?>
)
