package siarhei.luskanau.pixabayeye.core.pref

import kotlinx.coroutines.flow.Flow

interface PrefService {

    fun getUserPreferenceContent(): Flow<String?>

    fun getPixabayApiKey(): Flow<String?>

    suspend fun setPixabayApiKey(apikey: String?)
}
