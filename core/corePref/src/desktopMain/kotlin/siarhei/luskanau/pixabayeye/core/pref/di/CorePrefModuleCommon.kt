package siarhei.luskanau.pixabayeye.core.pref.di

import org.koin.dsl.module
import siarhei.luskanau.pixabayeye.core.pref.PrefService
import siarhei.luskanau.pixabayeye.core.pref.PrefServiceDataStore

val corePrefModule =
    module {

        single<PrefService> {
            PrefServiceDataStore(
                prefPathProvider = get(),
            )
        }
    }
