package siarhei.luskanau.pixabayeye.ui.login

import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.flowOf

// @Preview
@Composable
fun LoginComposablePreview() = LoginComposable(
    loginVewState = LoginVewState(apiKeyFlow = flowOf("apikey")),
    onInit = {},
    onUpdateClick = {},
    onCheckClick = {}
)
