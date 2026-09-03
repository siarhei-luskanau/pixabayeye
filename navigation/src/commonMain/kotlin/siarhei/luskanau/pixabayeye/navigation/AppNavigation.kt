package siarhei.luskanau.pixabayeye.navigation

import androidx.compose.runtime.mutableStateListOf
import androidx.navigation3.runtime.NavKey
import org.koin.core.annotation.Single
import siarhei.luskanau.pixabayeye.core.common.MediaType
import siarhei.luskanau.pixabayeye.ui.debug.DebugGraph
import siarhei.luskanau.pixabayeye.ui.media.details.MediaDetailsNavigationCallback
import siarhei.luskanau.pixabayeye.ui.media.list.MediaListNavigationCallback

@Single
internal class AppNavigation :
    MediaListNavigationCallback,
    MediaDetailsNavigationCallback {

    val backStack = mutableStateListOf<NavKey>(
        AppRoutes.MediaList(searchTerm = null, mediaType = MediaType.IMAGE)
    )

    var currentSearchTerm: String? = null
        private set

    override fun onSearchTermChanged(searchTerm: String) {
        currentSearchTerm = searchTerm
    }

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
