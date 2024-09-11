package siarhei.luskanau.pixabayeye.ui.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.flowOf
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import siarhei.luskanau.pixabayeye.common.PixabayTopAppBar
import siarhei.luskanau.pixabayeye.ui.common.resources.Res
import siarhei.luskanau.pixabayeye.ui.common.resources.screen_name_login

@Composable
fun LoginComposable(viewModel: LoginViewModel) {
    val apiKey = viewModel.getLoginViewState().apiKeyFlow.collectAsState(initial = null)
    Scaffold(
        topBar = { PixabayTopAppBar(title = stringResource(Res.string.screen_name_login)) }
    ) { contentPadding ->
        Column(modifier = Modifier.padding(contentPadding).fillMaxSize()) {
            OutlinedTextField(
                value = apiKey.value.orEmpty(),
                onValueChange = { viewModel.onUpdateClick(it) },
                label = { Text("PixabayApiKey") },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )

            Button(
                onClick = { viewModel.onCheckClick() },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                Text("Update")
            }
            LaunchedEffect(apiKey) {
                viewModel.onInit()
            }
        }
    }
}

@Preview
@Composable
fun LoginComposablePreview() = LoginComposable(
    viewModel = object : LoginViewModel() {
        override fun getLoginViewState(): LoginViewState =
            LoginViewState(apiKeyFlow = flowOf("apikey"))
        override fun onInit() = Unit
        override fun onUpdateClick(apiKey: String?) = Unit
        override fun onCheckClick() = Unit
    }
)
