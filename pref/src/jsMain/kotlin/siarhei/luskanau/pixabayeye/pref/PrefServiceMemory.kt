package siarhei.luskanau.pixabayeye.pref

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import pixabayeye.pref.BuildConfig

internal class PrefServiceMemory : PrefService {

    private val prefFlow: MutableStateFlow<PrefData> by lazy {
        MutableStateFlow(
            PrefData(pixabayApiKey = BuildConfig.PIXABAY_API_KEY),
        )
    }

    override fun getPixabayApiKey(): Flow<String?> =
        prefFlow.map { it.pixabayApiKey }

    override suspend fun setPixabayApiKey(apikey: String?) {
        val newPrefData = prefFlow.first().copy(pixabayApiKey = apikey)
        prefFlow.emit(newPrefData)
    }
}
