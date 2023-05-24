package siarhei.luskanau.pixabayeye.di

import android.content.Context
import kotlinx.coroutines.runBlocking
import okio.Path
import okio.Path.Companion.toPath
import org.koin.core.module.Module
import org.koin.dsl.module
import siarhei.luskanau.pixabayeye.core.DispatcherSet
import siarhei.luskanau.pixabayeye.pref.PrefPathProvider

actual val platformModule: Module = module {
    single<PrefPathProvider> {
        val context: Context = get()
        val dispatcherSet: DispatcherSet = get()
        object : PrefPathProvider {
            override fun get(): Path =
                runBlocking(dispatcherSet.ioDispatcher()) {
                    val file = context.filesDir.resolve("app.pref.json")
                    file.absolutePath.toPath()
                }
        }
    }
}
