package siarhei.luskanau.pixabayeye.ui.splash

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun SplashView(
    splashVewState: Flow<SplashVewState>,
    onSplashComplete: suspend () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    Text(
        text = "Splash",
        style = MaterialTheme.typography.h4,
        modifier = Modifier.padding(16.dp),
    )
    coroutineScope.launch {
        onSplashComplete.invoke()
    }
}
