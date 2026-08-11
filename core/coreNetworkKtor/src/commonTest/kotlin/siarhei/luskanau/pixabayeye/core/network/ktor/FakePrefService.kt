package siarhei.luskanau.pixabayeye.core.network.ktor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import siarhei.luskanau.pixabayeye.core.pref.PrefService

internal class FakePrefService(private val apiKey: String? = "test-api-key") : PrefService {
    override fun getUserPreferenceContent(): Flow<String?> = flowOf(null)
    override fun getPixabayApiKey(): Flow<String?> = flowOf(apiKey)
    override suspend fun setPixabayApiKey(apikey: String?) = Unit
}
