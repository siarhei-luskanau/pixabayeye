package siarhei.luskanau.pixabayeye.ui.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun LoginView(
    loginVewState: LoginVewState,
    modifier: Modifier,
    onInit: suspend () -> Unit,
    onUpdateClick: suspend (String) -> Unit,
    onCheckClick: suspend () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val apiKey = loginVewState.apiKeyFlow.collectAsState(initial = null)

    Column(modifier = modifier.fillMaxSize()) {
        @OptIn(ExperimentalMaterial3Api::class)
        OutlinedTextField(
            value = apiKey.value.orEmpty(),
            onValueChange = {
                coroutineScope.launch { onUpdateClick.invoke(it) }
            },
            label = { Text("PixabayApiKey") },
            modifier = Modifier.fillMaxWidth().padding(16.dp),
        )

        Button(
            onClick = {
                coroutineScope.launch { onCheckClick.invoke() }
            },
            modifier = Modifier.fillMaxWidth().padding(16.dp),
        ) {
            Text("Update")
        }
        LaunchedEffect(apiKey) {
            onInit.invoke()
        }
    }
}
