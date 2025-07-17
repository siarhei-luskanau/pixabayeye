package siarhei.luskanau.pixabayeye.ui.debug.datastore

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided
import siarhei.luskanau.pixabayeye.core.pref.PrefService

@Factory
class DatastoreViewModel(@Provided private val prefService: PrefService) : ViewModel() {

    val viewState: Flow<String?> = prefService.getUserPreferenceContent()
}
