package siarhei.luskanau.pixabayeye.ui.debug.datastore

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import siarhei.luskanau.pixabayeye.core.pref.PrefService

class DatastoreViewModel(prefService: PrefService) : ViewModel() {

    val viewState: Flow<String?> = prefService.getUserPreferenceContent()
}
