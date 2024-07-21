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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import siarhei.luskanau.pixabayeye.common.PixabayTopAppBar
import siarhei.luskanau.pixabayeye.ui.common.resources.Res
import siarhei.luskanau.pixabayeye.ui.common.resources.screen_name_login

@Composable
fun LoginComposable(
    loginVewState: LoginVewState,
    onInit: suspend () -> Unit,
    onUpdateClick: suspend (String) -> Unit,
    onCheckClick: suspend () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val apiKey = loginVewState.apiKeyFlow.collectAsState(initial = null)
    Scaffold(
        topBar = { PixabayTopAppBar(title = stringResource(Res.string.screen_name_login)) }
    ) { contentPadding ->
        Column(modifier = Modifier.padding(contentPadding).fillMaxSize()) {
            OutlinedTextField(
                value = apiKey.value.orEmpty(),
                onValueChange = {
                    coroutineScope.launch { onUpdateClick.invoke(it) }
                },
                label = { Text("PixabayApiKey") },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )

            Button(
                onClick = {
                    coroutineScope.launch { onCheckClick.invoke() }
                },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                Text("Update")
            }
            LaunchedEffect(apiKey) {
                onInit.invoke()
            }
        }
    }
}
