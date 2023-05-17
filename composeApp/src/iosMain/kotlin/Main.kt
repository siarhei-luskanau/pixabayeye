import androidx.compose.ui.window.ComposeUIViewController
import org.koin.core.Koin
import platform.UIKit.UIViewController
import siarhei.luskanau.compose.multiplatform.pixabayeye.App

fun MainViewController(koin: Koin): UIViewController {
    return ComposeUIViewController {
        App(koin = koin)
    }
}
