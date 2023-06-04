package siarhei.luskanau.pixabayeye.ui.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun SplashView(
    modifier: Modifier,
    onSplashComplete: suspend () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize(),
    ) {
        Text(
            text = "PixabayEye",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        )
    }
    coroutineScope.launch {
        onSplashComplete.invoke()
    }
}
