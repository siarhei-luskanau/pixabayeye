package siarhei.luskanau.pixabayeye.navigation

import androidx.navigation.NavHostController
import siarhei.luskanau.pixabayeye.ui.debug.DebugGraph
import siarhei.luskanau.pixabayeye.ui.image.details.ImageDetailsNavigationCallback
import siarhei.luskanau.pixabayeye.ui.image.list.ImageListNavigationCallback
import siarhei.luskanau.pixabayeye.ui.video.details.VideoDetailsNavigationCallback
import siarhei.luskanau.pixabayeye.ui.video.list.VideoListNavigationCallback

class AppNavigation(private val navHostController: NavHostController) :
    VideoListNavigationCallback,
    VideoDetailsNavigationCallback,
    ImageDetailsNavigationCallback,
    ImageListNavigationCallback {

    override fun goBack() {
        navHostController.popBackStack()
    }

    override fun onSearchScreenImageClicked(imageId: Long) {
        navHostController.navigate(
            AppRoutes.ImageDetails(imageId = imageId)
        )
    }

    override fun onImageTagClicked(tag: String) {
        navHostController.navigate(
            AppRoutes.ImageList(searchTerm = tag)
        )
    }

    override fun onVideoListScreenVideoClicked(videoId: Long) {
        navHostController.navigate(
            AppRoutes.VideoDetails(videoId = videoId)
        )
    }

    override fun onDebugScreenClicked() {
        navHostController.navigate(DebugGraph)
    }
}
