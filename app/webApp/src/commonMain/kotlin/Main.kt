import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import siarhei.luskanau.pixabayeye.app.KoinApp

@OptIn(ExperimentalComposeUiApi::class)
fun main() = ComposeViewport {
    KoinApp()
}
