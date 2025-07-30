package siarhei.luskanau.pixabayeye.ui.image.list

interface ImageListNavigationCallback {
    fun onSearchScreenImageClicked(imageId: Long)
    fun onImageTagClicked(tag: String)
    fun onDebugScreenClicked()
    fun goBack()
}
