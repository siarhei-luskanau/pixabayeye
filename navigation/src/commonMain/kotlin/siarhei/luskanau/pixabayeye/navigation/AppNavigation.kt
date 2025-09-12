package siarhei.luskanau.pixabayeye.navigation

import androidx.navigation3.runtime.NavKey
import siarhei.luskanau.pixabayeye.ui.debug.DebugGraph
import siarhei.luskanau.pixabayeye.ui.image.details.ImageDetailsNavigationCallback
import siarhei.luskanau.pixabayeye.ui.image.list.ImageListNavigationCallback
import siarhei.luskanau.pixabayeye.ui.video.details.VideoDetailsNavigationCallback
import siarhei.luskanau.pixabayeye.ui.video.list.VideoListNavigationCallback

internal class AppNavigation(private val backStack: MutableList<NavKey>) :
    VideoListNavigationCallback,
    VideoDetailsNavigationCallback,
    ImageDetailsNavigationCallback,
    ImageListNavigationCallback {

    override fun goBack() {
        backStack.removeLastOrNull()
    }

    override fun onSearchScreenImageClicked(imageId: Long) {
        backStack.add(AppRoutes.ImageDetails(imageId = imageId))
    }

    override fun onImageTagClicked(tag: String) {
        backStack.add(AppRoutes.ImageList(searchTerm = tag))
    }

    override fun onVideoListScreenVideoClicked(videoId: Long) {
        backStack.add(AppRoutes.VideoDetails(videoId = videoId))
    }

    override fun onDebugScreenClicked() {
        backStack.add(DebugGraph)
    }
}
