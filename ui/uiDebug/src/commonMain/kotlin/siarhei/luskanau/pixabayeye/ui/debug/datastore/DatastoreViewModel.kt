package siarhei.luskanau.pixabayeye.ui.debug.datastore

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow

abstract class DatastoreViewModel : ViewModel() {
    abstract val viewState: Flow<String?>
}
