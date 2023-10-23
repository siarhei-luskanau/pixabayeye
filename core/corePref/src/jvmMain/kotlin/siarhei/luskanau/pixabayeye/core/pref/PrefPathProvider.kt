package siarhei.luskanau.pixabayeye.core.pref

import okio.Path

interface PrefPathProvider {
    fun get(): Path
}
