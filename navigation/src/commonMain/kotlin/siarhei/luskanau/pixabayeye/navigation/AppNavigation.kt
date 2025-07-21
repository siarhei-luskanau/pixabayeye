package siarhei.luskanau.pixabayeye.navigation

import androidx.navigation.NavHostController
import siarhei.luskanau.pixabayeye.ui.debug.DebugGraph
import siarhei.luskanau.pixabayeye.ui.image.details.ImageDetailsNavigationCallback
import siarhei.luskanau.pixabayeye.ui.image.list.ImageListNavigationCallback

class AppNavigation(private val navHostController: NavHostController) :
    ImageDetailsNavigationCallback,
    ImageListNavigationCallback {

    override fun goBack() {
        navHostController.popBackStack()
    }

    override fun onSearchScreenImageClicked(imageId: Long) {
        navHostController.navigate(
            AppRoutes.Details(imageId = imageId)
        )
    }

    override fun onDebugScreenClicked() {
        navHostController.navigate(DebugGraph)
    }
}
