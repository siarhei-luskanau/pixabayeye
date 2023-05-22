package siarhei.luskanau.pixabayeye.di

import kotlinx.cinterop.ObjCClass
import kotlinx.cinterop.getOriginalKotlinClass
import okio.Path
import okio.Path.Companion.toPath
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.module
import platform.Foundation.NSBundle
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask
import siarhei.luskanau.pixabayeye.pref.PrefPathProvider

fun initKoinIos(
    bundle: NSBundle,
): KoinApplication = initKoin(
    module {},
)

actual val platformModule: Module = module {
    single<PrefPathProvider> {
        val file = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )?.path + Path.DIRECTORY_SEPARATOR + "app.pref.json"
        object : PrefPathProvider {
            override fun get(): Path = file.toPath()
        }
    }
}

fun Koin.get(objCClass: ObjCClass): Any {
    val kClazz = getOriginalKotlinClass(objCClass)!!
    return get(kClazz)
}

fun Koin.get(objCClass: ObjCClass, qualifier: Qualifier?, parameter: Any): Any {
    val kClazz = getOriginalKotlinClass(objCClass)!!
    return get(kClazz, qualifier) { parametersOf(parameter) }
}
