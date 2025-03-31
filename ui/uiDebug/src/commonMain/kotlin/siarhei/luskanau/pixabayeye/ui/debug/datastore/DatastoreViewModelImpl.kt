package siarhei.luskanau.pixabayeye.ui.debug.datastore

import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided
import siarhei.luskanau.pixabayeye.core.pref.PrefService

@Factory
internal class DatastoreViewModelImpl(@Provided private val prefService: PrefService) :
    DatastoreViewModel() {

    override val viewState: Flow<String?> = prefService.getUserPreferenceContent()
}
