package siarhei.luskanau.pixabayeye.pref

import okio.Path

interface PrefPathProvider {
    fun get(): Path
}
