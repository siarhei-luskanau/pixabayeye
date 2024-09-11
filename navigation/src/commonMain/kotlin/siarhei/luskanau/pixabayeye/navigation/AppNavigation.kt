package siarhei.luskanau.pixabayeye.navigation

import androidx.navigation.NavHostController
import siarhei.luskanau.pixabayeye.ui.details.DetailsNavigationCallback
import siarhei.luskanau.pixabayeye.ui.login.LoginNavigationCallback
import siarhei.luskanau.pixabayeye.ui.search.SearchNavigationCallback
import siarhei.luskanau.pixabayeye.ui.splash.SplashNavigationCallback
import siarhei.luskanau.pixabayeye.ui.splash.SplashNavigationCallback.LoginState

class AppNavigation(private val navHostController: NavHostController) :
    DetailsNavigationCallback,
    LoginNavigationCallback,
    SearchNavigationCallback,
    SplashNavigationCallback {

    override fun goBack() {
        navHostController.popBackStack()
    }

    override fun onLoginScreenCompleted() {
        navHostController.navigate(AppRoutes.Search) {
            popUpTo(AppRoutes.Login) {
                inclusive = true
            }
        }
    }

    override fun onSearchScreenImageClicked(imageId: Long) {
        navHostController.navigate(
            AppRoutes.Details(imageId = imageId)
        )
    }

    override fun onSearchScreenHomeClick() {
        navHostController.navigate(AppRoutes.Search) {
            popUpTo(AppRoutes.Search) {
                inclusive = true
            }
        }
    }

    override fun onSearchScreenLoginClick() {
        navHostController.navigate(AppRoutes.Login) {
            popUpTo(AppRoutes.Search) {
                inclusive = true
            }
        }
    }

    override fun onSplashScreenCompleted(loginState: LoginState) = navHostController.navigate(
        route = when (loginState) {
            LoginState.HasLogin -> AppRoutes.Search
            LoginState.NoLogin -> AppRoutes.Login
        }
    ) {
        popUpTo(AppRoutes.Splash) {
            inclusive = true
        }
    }
}
