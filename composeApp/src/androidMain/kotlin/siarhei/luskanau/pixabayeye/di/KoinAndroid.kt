package siarhei.luskanau.pixabayeye.di

import android.content.Context
import okio.Path
import okio.Path.Companion.toPath
import org.koin.core.module.Module
import org.koin.dsl.module
import siarhei.luskanau.pixabayeye.pref.PrefPathProvider

actual val platformModule: Module = module {
    single<PrefPathProvider> {
        val context: Context = get()
        val file = context.filesDir.resolve("app.pref.json")
        object : PrefPathProvider {
            override fun get(): Path = file.absolutePath.toPath()
        }
    }
}
