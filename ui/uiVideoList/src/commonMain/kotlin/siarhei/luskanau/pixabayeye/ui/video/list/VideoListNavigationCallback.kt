package siarhei.luskanau.pixabayeye.ui.video.list

interface VideoListNavigationCallback {
    fun onVideoListScreenVideoClicked(videoId: Long)
    fun onDebugScreenClicked()
    fun goBack()
}
