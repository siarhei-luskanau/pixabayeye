package siarhei.luskanau.pixabayeye.navigation

import androidx.navigation3.runtime.NavKey
import siarhei.luskanau.pixabayeye.core.common.MediaType
import siarhei.luskanau.pixabayeye.ui.debug.DebugGraph
import siarhei.luskanau.pixabayeye.ui.media.details.MediaDetailsNavigationCallback
import siarhei.luskanau.pixabayeye.ui.media.list.MediaListNavigationCallback

internal class AppNavigation(private val backStack: MutableList<NavKey>) :
    MediaListNavigationCallback,
    MediaDetailsNavigationCallback {

    override fun goBack() {
        if (backStack.size > 1) {
            backStack.removeLastOrNull()
        }
    }

    override fun onMediaListScreenItemClicked(id: Long, mediaType: MediaType) {
        backStack.add(AppRoutes.MediaDetails(id = id, mediaType = mediaType))
    }

    override fun onImageTagClicked(tag: String) {
        backStack.add(AppRoutes.MediaList(searchTerm = tag, mediaType = MediaType.IMAGE))
    }

    override fun onDebugScreenClicked() {
        backStack.add(DebugGraph)
    }
}
