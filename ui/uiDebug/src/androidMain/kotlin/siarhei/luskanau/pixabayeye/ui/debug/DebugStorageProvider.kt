package siarhei.luskanau.pixabayeye.ui.debug

import android.content.Context
import androidx.datastore.core.Storage
import androidx.datastore.core.okio.OkioSerializer
import androidx.datastore.core.okio.OkioStorage
import kotlinx.coroutines.runBlocking
import okio.FileSystem
import okio.Path.Companion.toPath
import org.koin.core.annotation.Single
import siarhei.luskanau.pixabayeye.core.common.DispatcherSet
import siarhei.luskanau.pixabayeye.core.pref.StorageProvider

@Single
internal class DebugStorageProvider(
    private val context: Context,
    private val dispatcherSet: DispatcherSet
) : StorageProvider {
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
