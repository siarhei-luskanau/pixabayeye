package siarhei.luskanau.pixabayeye.core.pref

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okio.FileSystem

internal class PrefServiceDataStore(
    private val prefPathProvider: PrefPathProvider,
) : PrefService {
    private val dataStore: DataStore<PrefData> by lazy {
        DataStoreFactory.create(
            storage =
                OkioStorage(
                    fileSystem = FileSystem.SYSTEM,
                    serializer = PrefSerializer(),
                    producePath = { prefPathProvider.get() },
                ),
            corruptionHandler = null,
            migrations = emptyList(),
        )
    }

    override fun getPixabayApiKey(): Flow<String?> = getFlowFromDataStore { it.pixabayApiKey }

    override suspend fun setPixabayApiKey(apikey: String?) {
        updateDataStore { it.copy(pixabayApiKey = apikey) }
    }

    private fun <T : Any> getFlowFromDataStore(mapData: (PrefData) -> T?): Flow<T?> = dataStore.data.map { mapData(it) }

    private suspend fun updateDataStore(update: (PrefData) -> PrefData) {
        dataStore.updateData { update(it) }
    }
}
