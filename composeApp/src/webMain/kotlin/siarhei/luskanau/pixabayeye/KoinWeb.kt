package siarhei.luskanau.pixabayeye

import androidx.datastore.core.Storage
import androidx.datastore.core.okio.OkioSerializer
import androidx.datastore.core.okio.WebLocalStorage
import org.koin.core.module.Module
import org.koin.dsl.module
import siarhei.luskanau.pixabayeye.core.pref.StorageProvider

actual val appPlatformModule: Module = module {
    single<StorageProvider> {
        object : StorageProvider {
            override fun <T> getStorage(serializer: OkioSerializer<T>): Storage<T> =
                WebLocalStorage(
                    serializer = serializer,
                    name = "app.pref.json"
                )
        }
    }
}
