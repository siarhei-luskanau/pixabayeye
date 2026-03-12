package siarhei.luskanau.pixabayeye.core.pref

import org.koin.dsl.module

val corePrefModule = module {
    single { PrefSerializer() }
    single<PrefService> { PrefServiceDataStore(storageProvider = get()) }
}
