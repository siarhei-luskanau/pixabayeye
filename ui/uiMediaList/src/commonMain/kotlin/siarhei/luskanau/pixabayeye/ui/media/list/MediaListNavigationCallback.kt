package siarhei.luskanau.pixabayeye.ui.media.list

import siarhei.luskanau.pixabayeye.core.common.MediaType

interface MediaListNavigationCallback {
    fun onMediaListScreenItemClicked(id: Long, mediaType: MediaType)
    fun onImageTagClicked(tag: String)
    fun onDebugScreenClicked()
    fun goBack()
}
