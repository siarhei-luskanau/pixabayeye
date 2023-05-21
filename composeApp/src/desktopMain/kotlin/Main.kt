import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import org.koin.dsl.module
import siarhei.luskanau.pixabayeye.di.initKoin
import siarhei.luskanau.pixabayeye.ui.App

fun main() = application {
    Window(
        title = "Compose App",
        state = rememberWindowState(width = 800.dp, height = 600.dp),
        onCloseRequest = ::exitApplication,
    ) {
        App(
            appViewModel = initKoin(
                module {},
            ).koin.get(),
        )
    }
}
