import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController
import siarhei.luskanau.pixabayeye.KoinApp

fun mainViewController(): UIViewController = ComposeUIViewController {
    KoinApp()
}
