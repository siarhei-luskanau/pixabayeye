import UIKit
import SwiftUI
import ComposeApp

@main
struct iosApp: App {
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}

struct ContentView: View {
    var body: some View {
        ComposeView().ignoresSafeArea(.all)
    }
}

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainKt.mainViewController(
            koin: KoinIosKt.doInitKoinIos(bundle: Bundle.main).koin
        )
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}
