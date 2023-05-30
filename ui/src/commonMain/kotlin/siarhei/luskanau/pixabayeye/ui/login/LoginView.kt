package siarhei.luskanau.pixabayeye.ui.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun LoginView(
    loginVewStateFlow: Flow<LoginVewState>,
    onTextUpdated: suspend (String?) -> Unit,
    onClick: suspend () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val loginVewState = loginVewStateFlow.collectAsState(initial = null)

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "PixabayApiKey",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        )

        @OptIn(ExperimentalMaterial3Api::class)
        OutlinedTextField(
            value = loginVewState.value?.apiKey.orEmpty(),
            onValueChange = {
                coroutineScope.launch {
                    onTextUpdated.invoke(it)
                }
            },
            label = { Text("PixabayApiKey") },
            modifier = Modifier.fillMaxWidth().padding(16.dp),
        )

        Button(
            onClick = {
                coroutineScope.launch {
                    onClick.invoke()
                }
            },
            modifier = Modifier.fillMaxWidth().padding(16.dp),
        ) {
            Text("Update")
        }
    }
}
