package siarhei.luskanau.pixabayeye.di

import okio.Path
import okio.Path.Companion.toPath
import org.koin.core.module.Module
import org.koin.dsl.module
import siarhei.luskanau.pixabayeye.pref.PrefPathProvider
import java.io.File

actual val platformModule: Module = module {
    single<PrefPathProvider> {
        val file = File.createTempFile("temp_", "app.pref.json")
        object : PrefPathProvider {
            override fun get(): Path = file.absolutePath.toPath()
        }
    }
}
