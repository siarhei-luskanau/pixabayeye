package siarhei.luskanau.pixabayeye.ui.search

interface SearchNavigationCallback {
    fun onSearchScreenImageClicked(imageId: Long)
    fun onSearchScreenHomeClick()
    fun onSearchScreenLoginClick()
}
