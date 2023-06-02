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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun LoginView(
    loginVewState: LoginVewState,
    onUpdateClick: suspend (String) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    var apiKey by remember { mutableStateOf(loginVewState.apiKey.orEmpty()) }

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
            value = apiKey,
            onValueChange = { apiKey = it },
            label = { Text("PixabayApiKey") },
            modifier = Modifier.fillMaxWidth().padding(16.dp),
        )

        Button(
            onClick = {
                coroutineScope.launch { onUpdateClick.invoke(apiKey) }
            },
            modifier = Modifier.fillMaxWidth().padding(16.dp),
        ) {
            Text("Update")
        }
    }
}
