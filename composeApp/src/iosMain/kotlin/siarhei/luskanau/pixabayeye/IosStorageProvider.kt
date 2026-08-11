package siarhei.luskanau.pixabayeye

import androidx.datastore.core.Storage
import androidx.datastore.core.okio.OkioSerializer
import androidx.datastore.core.okio.OkioStorage
import kotlinx.cinterop.ExperimentalForeignApi
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import org.koin.core.annotation.Single
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask
import siarhei.luskanau.pixabayeye.core.pref.StorageProvider

@OptIn(ExperimentalForeignApi::class)
@Single
internal class IosStorageProvider : StorageProvider {
    override fun <T> getStorage(serializer: OkioSerializer<T>): Storage<T> {
        val file = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null
        )?.path +
            Path.DIRECTORY_SEPARATOR +
            "app.pref.json"
        return OkioStorage(
            fileSystem = FileSystem.SYSTEM,
            serializer = serializer,
            producePath = { file.toPath() }
        )
    }
}
