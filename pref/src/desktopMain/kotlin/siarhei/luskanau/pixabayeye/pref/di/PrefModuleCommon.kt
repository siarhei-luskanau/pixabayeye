package siarhei.luskanau.pixabayeye.pref.di

import org.koin.dsl.module
import siarhei.luskanau.pixabayeye.pref.PrefService
import siarhei.luskanau.pixabayeye.pref.PrefServiceDataStore

val prefModule = module {

    single<PrefService> {
        PrefServiceDataStore(
            prefPathProvider = get(),
        )
    }
}
