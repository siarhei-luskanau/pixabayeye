package siarhei.luskanau.pixabayeye

import android.content.Context
import androidx.datastore.core.Storage
import androidx.datastore.core.okio.OkioSerializer
import androidx.datastore.core.okio.OkioStorage
import kotlinx.coroutines.runBlocking
import okio.FileSystem
import okio.Path.Companion.toPath
import org.koin.core.module.Module
import org.koin.dsl.module
import siarhei.luskanau.pixabayeye.core.common.DispatcherSet
import siarhei.luskanau.pixabayeye.core.pref.StorageProvider

actual val appPlatformModule: Module = module {
    single<StorageProvider> {
        val context: Context = get()
        val dispatcherSet: DispatcherSet = get()
        object : StorageProvider {
            override fun <T> getStorage(serializer: OkioSerializer<T>): Storage<T> = OkioStorage(
                fileSystem = FileSystem.SYSTEM,
                serializer = serializer,
                producePath = {
                    runBlocking(dispatcherSet.ioDispatcher()) {
                        val file = context.filesDir.resolve("app.pref.json")
                        file.absolutePath.toPath()
                    }
                }
            )
        }
    }
}
