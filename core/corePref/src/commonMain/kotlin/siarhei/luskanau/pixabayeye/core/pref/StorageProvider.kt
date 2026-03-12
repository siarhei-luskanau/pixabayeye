package siarhei.luskanau.pixabayeye.core.pref

import androidx.datastore.core.Storage
import androidx.datastore.core.okio.OkioSerializer

interface StorageProvider {
    fun <T> getStorage(serializer: OkioSerializer<T>): Storage<T>
}
