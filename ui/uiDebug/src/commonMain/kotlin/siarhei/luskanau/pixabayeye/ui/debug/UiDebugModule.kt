package siarhei.luskanau.pixabayeye.ui.debug

import org.koin.dsl.module
import siarhei.luskanau.pixabayeye.ui.debug.datastore.DatastoreViewModel

val uiDebugModule = module {
    factory { DatastoreViewModel(prefService = get()) }
}
