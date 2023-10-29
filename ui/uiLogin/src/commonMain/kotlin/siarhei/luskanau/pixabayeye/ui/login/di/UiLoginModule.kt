package siarhei.luskanau.pixabayeye.ui.login.di

import org.koin.dsl.module
import siarhei.luskanau.pixabayeye.ui.login.LoginVewModel

val uiLoginModule =
    module {
        single {
            val onLoginComplete: () -> Unit = it.get()
            LoginVewModel(
                prefService = get(),
                pixabayApiService = get(),
                onLoginComplete = onLoginComplete
            )
        }
    }
