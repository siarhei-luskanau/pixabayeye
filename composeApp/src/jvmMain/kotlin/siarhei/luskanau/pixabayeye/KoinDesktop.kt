package siarhei.luskanau.pixabayeye

import java.io.File
import kotlinx.coroutines.runBlocking
import okio.Path
import okio.Path.Companion.toPath
import org.koin.core.module.Module
import org.koin.dsl.module
import siarhei.luskanau.pixabayeye.core.common.DispatcherSet
import siarhei.luskanau.pixabayeye.core.pref.PrefPathProvider

actual val appPlatformModule: Module =
    module {
        single<PrefPathProvider> {
            val dispatcherSet: DispatcherSet = get()
            object : PrefPathProvider {
                override fun get(): Path = runBlocking(dispatcherSet.ioDispatcher()) {
                    val file = File.createTempFile("temp_", "app.pref.json")
                    file.absolutePath.toPath()
                }
            }
        }
    }
