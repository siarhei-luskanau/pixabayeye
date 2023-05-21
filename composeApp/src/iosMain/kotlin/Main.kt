import androidx.compose.ui.window.ComposeUIViewController
import org.koin.core.Koin
import platform.UIKit.UIViewController
import siarhei.luskanau.pixabayeye.ui.App

fun MainViewController(koin: Koin): UIViewController {
    return ComposeUIViewController {
        App(appViewModel = koin.get())
    }
}
