package siarhei.luskanau.pixabayeye.navigation

import androidx.navigation.NavHostController
import siarhei.luskanau.pixabayeye.ui.details.DetailsNavigationCallback
import siarhei.luskanau.pixabayeye.ui.search.SearchNavigationCallback

class AppNavigation(private val navHostController: NavHostController) :
    DetailsNavigationCallback,
    SearchNavigationCallback {

    override fun goBack() {
        navHostController.popBackStack()
    }

    override fun onSearchScreenImageClicked(imageId: Long) {
        navHostController.navigate(
            AppRoutes.Details(imageId = imageId)
        )
    }
}
