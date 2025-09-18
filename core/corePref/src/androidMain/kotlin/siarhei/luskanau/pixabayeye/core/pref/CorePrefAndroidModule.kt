package siarhei.luskanau.pixabayeye.core.pref

import org.koin.dsl.module

actual val corePrefModule = module {
    single { PrefSerializer() }
    single<PrefService> { PrefServiceDataStore(prefPathProvider = get()) }
}
