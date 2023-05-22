package siarhei.luskanau.pixabayeye.pref

import kotlinx.coroutines.flow.Flow

interface PrefService {
    fun getPixabayApiKey(): Flow<String?>
    suspend fun setPixabayApiKey(apikey: String?)
}
