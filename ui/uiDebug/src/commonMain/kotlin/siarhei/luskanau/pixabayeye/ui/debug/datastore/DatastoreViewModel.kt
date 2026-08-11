package siarhei.luskanau.pixabayeye.ui.debug.datastore

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.KoinViewModel
import siarhei.luskanau.pixabayeye.core.pref.PrefService

@KoinViewModel
class DatastoreViewModel(prefService: PrefService) : ViewModel() {

    val viewState: Flow<String?> = prefService.getUserPreferenceContent()
}
